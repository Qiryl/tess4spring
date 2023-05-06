package com.example.processor;

import com.example.processor.repository.KafkaMessage;
import com.example.processor.service.IStorageRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.grpc.ManagedChannelBuilder;
import io.grpc.example.storage.StorageServiceGrpc;
import io.grpc.example.storage.UpdateFileMetaRequest;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.kafka.annotation.KafkaListener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.concurrent.CountDownLatch;

@SpringBootApplication
public class ProcessorApplication {
	public static void main(String[] args) {
		SpringApplication.run(ProcessorApplication.class, args);
	}

	private final CountDownLatch latch = new CountDownLatch(3);
	private final ObjectMapper mapper = new ObjectMapper();


	@Autowired
	@Qualifier("minio")
	private IStorageRepository storage;

	@KafkaListener(topics = "test2")
	public void listen(ConsumerRecord<?, ?> record) throws Exception {

		var msg = mapper.readValue(record.value().toString(), KafkaMessage.class);

		var ddir = new File("data/download");
		if (!ddir.exists()) {
			ddir.mkdirs();
		}
		var file = new File(ddir, msg.getId() + "." + msg.getType());

		var read = Channels.newChannel(new URL(msg.getLink()).openStream());
		var out = new FileOutputStream(file);
		out.getChannel().transferFrom(read, 0, Long.MAX_VALUE);
		out.close();

		var pdir = new File("data/processed");
		if (!pdir.exists()) {
			pdir.mkdirs();
		}
		var command = String.format("tesseract %s %s -l eng %s", file.getAbsolutePath(), msg.getId(), msg.getOutType());
		var pb = new ProcessBuilder(command.split(" "));
		pb.directory(pdir);

		var p = pb.start();
		p.waitFor();

		var link = storage.save(new File(pdir, msg.getId() + "." + msg.getOutType()));
		var chan = ManagedChannelBuilder.forAddress("localhost", 8081).usePlaintext().build();
		var stub = StorageServiceGrpc.newBlockingStub(chan);
		stub.updateFileMeta(UpdateFileMetaRequest
				.newBuilder()
				.setId(msg.getId())
				.setLink(link)
				.build());

		latch.countDown();
	}
}