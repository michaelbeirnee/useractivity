import java.io.IOException;
import java.nio.file.Files; 
import java.nio.file.Path; 
import java.util.ArrayList; 
import java.util.List; 
import java.util.Optional; 

public class TaskManager {
    
    private List<Task> tasks;       //list of tasks 
    private final Path FILE_PATH = Path.of("tasks.json"); 
    
    public TaskManager(){
        this.tasks = loadTasks(); 
    }

    //reads json file 
    private List<Task> loadTasks(){
        
        List<Task> stored_tasks = new ArrayList<>(); 

       if(!Files.exists(FILE_PATH)){
            return new ArrayList<>(); 
       } 

       try{
        String jsonContent = Files.readString(FILE_PATH); 
        String[] tasklist = jsonContent.replace("[", "").replace("]", "").split(("},"));
        
        for(String task : tasklist){
            String taskJson = task.trim();
            if (taskJson.isEmpty()) {
                continue;
            }

            if(!taskJson.endsWith("}")){
                taskJson = taskJson + "}"; 
                stored_tasks.add(Task.fromJson(taskJson)); 
            }else{
                stored_tasks.add(Task.fromJson(taskJson)); 
            }
        }
       } catch(IOException e){
            e.printStackTrace();
       }

        return stored_tasks; 
    }

    public void saveTasks(){
        StringBuilder sb = new StringBuilder(); 
        sb.append("[\n");

        for(int i = 0; i < tasks.size(); i++){
            sb.append(tasks.get(i).toJson());
            if(i < tasks.size() - 1){
                sb.append(",\n"); 
            }
        }

        sb.append(
            "\n]"
        ); 

        String jsonContent = sb.toString(); 
        try{
            Files.writeString(FILE_PATH, jsonContent); 
        }catch(IOException e){
            e.printStackTrace(); 
        }
    }

    public void addtask(String description){
        Task new_task = new Task(description); 
        tasks.add(new_task); 
        System.out.println("Task Added Sucessfully"); 

    }

    public void updateTask(String id, String new_description){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task DNE")); 
        
        //calls update description in Task.java
        task.updateDescription(new_description); 

    }

    public void deleteTask(String id){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task DNE"));
        tasks.remove(task); 
    }

    public void markInProgress(String id){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("Task id not found"));
        task.markInProgress();
    }

    public void markDone(String id){
        Task task = findTask(id).orElseThrow(() -> new IllegalArgumentException("id not found"));
        task.markDone(); 
    }

    public void listTasks(String type){
        for(Task task : tasks){
            String status = task.getStatus().toString().strip(); 
            if(status.equals(type)){
                System.out.println(task.toString()); 
            }
        }
    }

    public Optional<Task> findTask(String id){
        return tasks.stream().filter((task) -> task.getID() == Integer.parseInt(id)).findFirst(); 
    }

}
