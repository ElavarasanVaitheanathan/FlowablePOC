package com.flowwable.poc.controller;

import com.flowwable.poc.domain.TaskRepresentation;
import com.flowwable.poc.service.InspectionRequestService;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class InspectionRequestController {
    @Autowired
    private InspectionRequestService inspectionRequestService;

    @RequestMapping(value = "/process", method = RequestMethod.POST)
    public String startProcessInstance(@RequestParam String processName) {
        return inspectionRequestService.startProcess(processName);
    }

    @RequestMapping(value = "/tasks", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasks(@RequestParam String assignee) {
        System.out.println("getTasks started  = " + assignee);
        List<Task> tasks = inspectionRequestService.getTasks(assignee);
        return buildTaskRepresentation(tasks);
    }

    @RequestMapping(value = "/tasksforGroup", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public List<TaskRepresentation> getTasksforGroup(@RequestParam String group) {
        System.out.println("getTasks for group started  = " + group);
        List<Task> tasks = inspectionRequestService.getTasksforGroup(group);
        return buildTaskRepresentation(tasks);
    }

    @RequestMapping(value = "/assignTask", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_VALUE)
    public String assignTask(@RequestParam String assignee, @RequestParam String group, @RequestParam String taskId) {
        System.out.println("assignTask started  = " + assignee + " taskId is " + taskId);
        return inspectionRequestService.assignTask(assignee, group, taskId);
    }

    @RequestMapping(value = "/approveOrRejectTask", method = RequestMethod.POST)
    public String approveOrRejectTask(@RequestParam boolean status, @RequestParam String taskId) {
        System.out.println("approveOrRejectTask started  = " + status + " " + taskId);
        return inspectionRequestService.approveOrRejectTask(status, taskId);

    }

    @RequestMapping(value = "/completeTask", method = RequestMethod.POST)
    public String completeTask(@RequestParam String taskId) {
        System.out.println("completeTask started  = " + taskId);
        return inspectionRequestService.completeTask(taskId);
    }

    @RequestMapping(value = "/processStatus", method = RequestMethod.POST)
    public String processStatus(@RequestParam String processId) {
        return inspectionRequestService.retrieveProcessStatus(processId);
    }

    @RequestMapping(value = "/printTaskInfo", method = RequestMethod.POST)
    public void printTaskInfo(@RequestParam String taskId) {
        System.out.println("taskInfo started  = " + taskId);
        inspectionRequestService.printTaskInfo(taskId);
    }

    @RequestMapping(value = "/printAllTaskInfo", method = RequestMethod.GET)
    public void printAllTaskInfo() {
        System.out.println("allTaskInfo started ");
        inspectionRequestService.printAllTaskInfo();
    }

    private List<TaskRepresentation> buildTaskRepresentation(List<Task> tasks) {
        return tasks.stream().map(task ->
                new TaskRepresentation(task.getId(), task.getName(), task.getAssignee())).collect(Collectors.toList());
    }
}
