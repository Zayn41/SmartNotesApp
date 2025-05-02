import java.util.*;
import java.time.LocalDateTime;

// Represents a single Note with title, description, priority, and creation date
class Note {
    private String title;
    private String description;
    private String priority;
    private LocalDateTime createDate;

    // Constructor
    public Note(String title, String description, String priority) {
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.createDate = LocalDateTime.now();
    }

    // Getter and Setter methods
    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getPriority() {
        return priority;
    }

    public LocalDateTime getCreatedDate() {
        return createDate;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPriority(String priority) {
        this.priority = priority;
    }

    public void displayNotes() {
        System.out.println("Title: " + title);
        System.out.println("Description: " + description);
        System.out.println("Priority: " + priority);
        System.out.println("Created on: " + createDate);
        System.out.println("--------------------------");
    }
}

// Notes Manager class for handling notes and user interactions
class NotesManager {
    private List<Note> notesList; // List to store all notes
    private Scanner sc; // for userinput

    // Constructor
    public NotesManager() {
        this.notesList = new ArrayList<>();
        this.sc = new Scanner(System.in);
    }

    // Function for adding notes
    public void addNotes() {
        System.out.print("Enter Title: ");
        String title = sc.nextLine();
        System.out.print("Enter Description: ");
        String description = sc.nextLine();
        System.out.print("Enter Priority (High/Medium/Low): ");
        String priority = sc.nextLine();

        Note notes = new Note(title, description, priority);
        notesList.add(notes);
        System.out.println("Notes added successfully!");
    }

    // Function for viewing notes
    public void viewNotes() {
        if(notesList.isEmpty()) {
            System.out.println("No notes to display.");
            return;
        }

        for(Note notes : notesList) {
            notes.displayNotes();
        }
    }

    // Function for searching notes
    public void searchNote() {
        System.out.print("Enter title keyword to search: ");
        String keyword = sc.nextLine();

        boolean found = false;
        for(Note note : notesList) {
            if(note.getTitle().toLowerCase().contains(keyword.toLowerCase())) {
                note.displayNotes();
                found = true;
            }
        }

        if(!found) {
            System.out.println("No notes found with given title.");
        }
    
    }

    // Function for deleting notes
    public void deleteNote() {
        System.out.print("Enter exact title of note to delete: ");
        String title = sc.nextLine();

        Iterator<Note> iterator = notesList.iterator();
        boolean deleted = false;
        while(iterator.hasNext()) {
            Note note = iterator.next();
            if(note.getTitle().equalsIgnoreCase(title) && !deleted) {
                iterator.remove();
                deleted = true;
                System.out.println("Note deleted successfully.");
                break;
            }
        }

        if(!deleted) {
            System.out.println("Note not found.");
        }
    }

    // Function for editing notes
    public void editNote() {
        System.out.print("Enter exact title of note to edit: ");
        String title = sc.nextLine();
        boolean edit = false;

        for(Note note : notesList) {
            if(note.getTitle().equalsIgnoreCase(title) && !edit) {
                System.out.print("Enter new description: ");
                String newDesc = sc.nextLine();
                System.out.print("Enter new priority (High/Medium/Low): ");
                String newPriority = sc.nextLine();
                note.setDescription(newDesc);
                note.setPriority(newPriority);
                System.out.println("Note updated successfully!");
                edit = true;
                return;
            }
        }

        if(!edit) {
            System.out.println("Note not found.");
        }
    }

    // Function for sorting notes by priority or created date
    public void sortNotes() {
        System.out.println("\nSort by:");
        System.out.println("1. Priority (High > Medium > Low)");
        System.out.println("2. Created Date (Newest First)");

        // Try-Catch for error handling
        try {
            System.out.print("Enter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());

            switch(choice) {
                case 1 -> {
                    notesList.sort((n1, n2) -> {
                        Map<String, Integer> priorityOrder = Map.of(
                            "High", 1,
                            "Medium", 2,
                            "Low", 3
                        );
                        return Integer.compare(
                            priorityOrder.getOrDefault(n1.getPriority(), 4),
                            priorityOrder.getOrDefault(n2.getPriority(), 4)
                        );
                    });
                    System.out.println("Notes sorted by priority.\n");
                    viewNotes();
                }
        
                case 2 -> {
                    notesList.sort((n1, n2) -> n2.getCreatedDate().compareTo(n1.getCreatedDate()));
                    System.out.println("Notes sorted by created date (newest first).\n");
                    viewNotes();
                }
        
                default -> System.out.println("Invalid sorting choice.");
            }
        } catch(NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }
    
}

// Main method to run the Smart Notes Organizer console application
public class SmartNotesApp {

    public static void main(String[] args) {
        NotesManager nm = new NotesManager();
        Scanner sc = new Scanner(System.in);

        boolean running = true;
        while(running) {
            // Display menu and handle user choices
            System.out.println("\n=== Smart Notes Organizer ===");
            System.out.println("1. Add Note");
            System.out.println("2. View All Notes");
            System.out.println("3. Search Note");
            System.out.println("4. Delete Note");
            System.out.println("5. Edit Note");
            System.out.println("6. Sort Notes");
            System.out.println("7. Exit");
            
            // Try-Catch for errors handling
            try {
                System.out.print("Enter your choice: ");
                int choice = sc.nextInt();
                sc.nextLine();
    
                switch(choice) {
                    case 1 -> nm.addNotes();
                    case 2 -> nm.viewNotes();
                    case 3 -> nm.searchNote();
                    case 4 -> nm.deleteNote();
                    case 5 -> nm.editNote();
                    case 6 -> nm.sortNotes();
                    case 7-> {
                        System.out.println("Thanks for using Smart Notes Organizer!");
                        return;
                    } 
                    default -> System.out.println("Invalid choice! Try again.");
                }
            } catch(InputMismatchException e) {
                System.out.println("Please enter a valid number!");
                sc.nextLine();
            }
        }

        sc.close();
        
    }
}
