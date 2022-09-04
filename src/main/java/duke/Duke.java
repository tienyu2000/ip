package duke;

import duke.storage.Storage;
import duke.exceptions.DukeException;
import duke.handlers.*;
import duke.models.TaskList;
import duke.services.Parser;
import duke.utils.Commands;

import java.util.Scanner;
import static duke.services.Ui.dukePrint;

public class Duke {
    private Storage storage;
    private final Parser parser;
    private TaskList taskList;

    /**
     * Constructor for a Duke instance.
     */
    public Duke() {
        parser = new Parser();
        storage = new Storage();
        taskList = new TaskList();
    }

    /**
     * Run method to execute the Duke instance.
     */
    public void run() {
        taskList = storage.load();
        Scanner s = new Scanner(System.in);
        dukePrint("Hello! What can I do for you, my highness?");

        while (true) {
            String input = s.nextLine();
            Commands command = parser.parseCommand(input);
            String[] wholeCommand = parser.parseFullCommand(input);

            try {
                switch (command) {
                case BYE:
                    ByeHandler.handle();
                    break;
                case TODO:
                    ToDoHandler.handle(taskList, wholeCommand[1]);
                    break;
                case DEADLINE:
                    DeadlineHandler.handle(taskList, wholeCommand[1]);
                    break;
                case EVENT:
                    EventHandler.handle(taskList, wholeCommand[1]);
                    break;
                case DELETE:
                    DeleteHandler.handle(taskList, wholeCommand[1]);
                    break;
                case LIST:
                    ListHandler.handle(taskList);
                    break;
                case MARK:
                    MarkHandler.handle(taskList, wholeCommand[1]);
                    break;
                case UNMARK:
                     UnmarkHandler.handle(taskList, wholeCommand[1]);
                     break;
                default:
                     dukePrint("Unknown command");
                     break;
                }
                storage.save(taskList);
            } catch (DukeException e) {
                dukePrint(e.getMessage());
            }
        }
    }

    /**
     * Main method. Initiates a Duke instance and runs it.
     * @param args Command line arguments.
     */
    public static void main(String[] args) {
        Duke duke = new Duke();
        duke.run();
    }
}
