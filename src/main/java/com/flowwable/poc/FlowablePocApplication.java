package com.flowwable.poc;

import com.flowwable.poc.service.InspectionRequestService;
import org.flowable.engine.RepositoryService;
import org.flowable.engine.RuntimeService;
import org.flowable.engine.TaskService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FlowablePocApplication {

    public static void main(String[] args) {
        SpringApplication.run(FlowablePocApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(final RepositoryService repositoryService,
                                  final RuntimeService runtimeService,
                                  final TaskService taskService, final InspectionRequestService inspectionRequestService) {

        return strings -> {
            inspectionRequestService.createDemoUsers();
            inspectionRequestService.printStateChangeInfo();
        };
    }
}