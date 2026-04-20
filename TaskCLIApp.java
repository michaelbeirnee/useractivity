public class TaskCLIApp {

    public static void main(String[] args){
        TaskManager taskManager = new TaskManager(); 

        if(args.length < 1){
            System.out.println("Usage: TaskCLIApp <command> [arguments]");
            return; 
        }

        String command = args[0]; 

        switch(command){
            case "add" : 
                if(args.length < 2){
                    System.out.println("Add description ");
                    return; 
                }
                taskManager.addtask(args[1]);
                break;

            case "update" : 
                if(args.length < 3){
                    System.out.println("New description ");
                    return; 
                }
                taskManager.updateTask(args[1], args[2] ); 
                System.out.println("Task updated"); 
                break;

            case "delete" : 
                if (args.length < 2) {
                    System.out.println("Usage Delete "); 
                    return;
                }
                taskManager.deleteTask(args[1]);
                System.out.println("Task deleted");
                break; 
            case "mark-in-progress" :
                if(args.length < 2){
                    System.out.println("Mark in progress ");
                    return;
                }
                taskManager.markInProgress(args[1]);
                System.out.println("Marked In Progress"); 
                break; 
            case "mark-done" :
                if(args.length < 2){
                    System.out.println("Marked Done"); 
                    return;
                }
                taskManager.markDone(args[1]);
                System.out.println("Marked Done"); 
                break; 
            case "list" : 
                if(args.length < 2){
                    taskManager.listTasks("all"); 
                }else{
                    Task.Status filterStatus; 
                    try{
                        filterStatus = Task.Status.valueOf(args[1].toUpperCase().replace("-", "_")); 
                    }catch (IllegalArgumentException e) {
                        System.out.println("Invalid Status");
                        return; 
                    }
                    taskManager.listTasks(filterStatus.toString()); 
                }
                break; 
            default : 
                System.out.println("Unknown Command");
                break; 
        }
        taskManager.saveTasks();
    }
}
