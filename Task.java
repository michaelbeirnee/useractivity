import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class Task{
    public enum Status {
        TO_DO, 
        IN_PROGRESS,
        DONE; 

        public static Status fromString(String value){
            return Status.valueOf(value.replace("\"", "").trim().toUpperCase().replace(" ", "_")); 
        }

        @Override
        public String toString(){
            return name().toLowerCase().replace("_", " "); 
        }
    }

    private int id;             //task id
    private String description; 
    private LocalDateTime createdAt; 
    private LocalDateTime updatedAt;
    private Status status;
    private static int lastId = 0;   
    private static final DateTimeFormatter formatter = DateTimeFormatter.ISO_LOCAL_DATE_TIME;

   
    public Task(String description){
        this.id = lastId++; 
        this.description = description; 
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
        this.status = Status.TO_DO;   
    }

    public int getID(){
        return id; 
    }

    public void markDone(){
        this.status = Status.DONE; 
        this.updatedAt = LocalDateTime.now(); 
    }

    public void markInProgress(){
        this.status = Status.IN_PROGRESS; 
        this.updatedAt = LocalDateTime.now(); 
    }


    public void updateDescription(String description){
        this.description = description; 
        this.updatedAt = LocalDateTime.now(); 
    }

    public String toJson(){
        return "{\"id\":\"" + id + "\", \"description\":\"" + description.strip() + "\", \"status\":\"" + status.toString() +
        "\", \"createdAt\":\"" + createdAt.format(formatter) + "\", \"updatedAt\":\"" + updatedAt.format(formatter) + "\"}";
    }

    public static Task fromJson(String json){
        String[] json1 = json.split(","); 

        String id = json1[0].split(":")[1].replace("{", "").replace("\"", "").trim(); 
        String description = json1[1].split(":")[1].replace("\"", "").trim(); 
        String statusString = json1[2].split(":")[1].replace("\"", "").trim(); 
        String createdAtStr = json1[3].split(":")[1].replace("\"", "").trim(); 
        String updatedAtStr = json1[4].split(":")[1].replace("}", "").replace("\"", "").trim(); 

        Status status = Status.fromString(statusString); 

        Task task = new Task(description); 
        task.id = Integer.parseInt(id); 
        task.status = status; 
        task.createdAt = LocalDateTime.parse(createdAtStr, formatter);
        task.updatedAt = LocalDateTime.parse(updatedAtStr, formatter); 
        
        if(Integer.parseInt(id) > lastId){
            lastId = Integer.parseInt(id) + 1; 
        }
        
        return task; 
    }

    public Status getStatus(){
        return status; 
    }

    public String toString(){
        return "id: " + id + ", description: " + description.strip() + ", status: " + status.toString() + ", created at: " + createdAt.format(formatter) + 
        ", updated at: " + updatedAt.format(formatter); 
    }
}
