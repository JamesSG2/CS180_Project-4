/**
 * creates the exception for when an invalid username is inputted
 * @author Shruti Shah
 *
 */

public class InvalidUsername extends Exception {
    public InvalidUsername() {
        super();
    }

    public InvalidUsername(String message) {
        super(message);
    }
}

