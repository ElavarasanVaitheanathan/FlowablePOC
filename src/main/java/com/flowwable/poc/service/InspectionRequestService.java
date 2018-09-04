package com.flowwable.poc.service;

import com.flowwable.poc.entity.User;
import com.flowwable.poc.repository.UserRepository;
import org.flowable.engine.*;
import org.flowable.engine.history.HistoricProcessInstance;
import org.flowable.task.api.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Transactional
public class InspectionRequestService {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RepositoryService repositoryService;

    public String startProcess(String processName) {
        String processId = runtimeService.startProcessInstanceByKey(processName).getId();
        System.out.println("process started " + processName + " id:" + processId);
        printStateChangeInfo();
        return "The Process "+processName+" has Started -  Id ="+processId ;
    }

    public List<Task> getTasks(String assignee) {
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();
        System.out.println(assignee + " assignee has nof of task = " + tasks.size());
        printTask(tasks);
        return tasks;
    }

    public String assignTask(String assignee, String group, String taskId) {
        List<Task> list = taskService.createTaskQuery().taskCandidateGroup(group).list();
        System.out.println("The group  = " + group + " has " + list.size() + " task");
        taskService.claim(taskId, assignee);
        List<Task> tasks = taskService.createTaskQuery().taskAssignee(assignee).list();
        System.out.println("After claimed : assignee has " + list.size() + " in his queue");
        return "Task" + taskId + " has been assigned to " + assignee;
    }

    public String approveOrRejectTask(boolean status, String taskId) {
        HashMap variables = new HashMap<String, Object>();
        variables.put("approved", status);
        taskService.complete(taskId, variables);
        return "TaskId " + taskId + " has been completed with status " + status;
    }

    public String completeTask(String taskId) {
        taskService.complete(taskId);
        System.out.println("Task " + taskId + " Completed");
        return "Task " + taskId + " Completed";
    }

    public List<Task> getTasksforGroup(String group) {
        List<Task> tasks = taskService.createTaskQuery().taskCandidateGroup(group).list();
        System.out.println("No of tasks for = " + group + "  " + tasks.size());
        return tasks;
    }

    public void printTaskInfo(String taskId) {
        taskService.createTaskQuery().list().forEach(task -> {
            if (task.getId().equalsIgnoreCase(taskId)) {
                System.out.println("Task Id() = " + task.getId() + " " + task.getAssignee() + "" + task.getName() + " " + task.getOwner());
            } else {
                System.out.println("No information for  = " + taskId);
            }
        });
    }

    public void printAllTaskInfo() {
        List<Task> list = taskService.createTaskQuery().list();
        printTask(list);
    }

    public void printStateChangeInfo() {

        System.out.println("StateChange information.............................");
        System.out.println("Number of tasks : " + taskService.createTaskQuery().count());
        List<Task> tasks = taskService.createTaskQuery().list();
        printTask(tasks);
    }

    private void printTask(List<Task> tasks) {
        if(tasks.size()>0){
            tasks.stream().forEach(task -> {
                System.out.println("Task task.getName() = [" + task.getName() + "]  Assignee is [" + task.getAssignee() + "] task id=[" + task.getId()+"]");
            });
        } else {
            System.out.println("There is no Task");
        }
    }

    public void createDemoUsers() {
        if (userRepository.findAll().size() == 0) {
            userRepository.save(new User("ela", "Elavarasan", "Vaidyanathan", new Date()));
            userRepository.save(new User("sundar", "Sundar", "Murugan", new Date()));

            userRepository.save(new User("conadmin", "Contractor Admin", "Contractor Admin", new Date()));
            userRepository.save(new User("con-pm", "Contractor PM", "Contractor PM", new Date()));
            userRepository.save(new User("consul-engineer", "Consultant Engineer", "Consultant Engineer", new Date()));
            userRepository.save(new User("consul-pm", "Consultant PM", "Consultant PM", new Date()));
        }
    }

    @Autowired
    ProcessEngine processEngine ;

    public String retrieveProcessStatus(String processId) {
        HistoryService historyService = processEngine.getHistoryService();
        HistoricProcessInstance historicProcessInstance =
                historyService.createHistoricProcessInstanceQuery().processInstanceId(processId).singleResult();
        if(historicProcessInstance!= null){
            System.out.println("Process instance end time: " + historicProcessInstance.getEndTime());
            return "The Process Id has ended @"+historicProcessInstance.getEndTime();
        }
        else{
            return "The Process Id is Invalid";
        }

    }
}