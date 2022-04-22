/**
 * creates exception for invalid password input
 * @author Shruti Shah
 */

public class InvalidPassword extends Exception {
    public InvalidPassword() {
        super();
    }

    public InvalidPassword(String message) {
        super(message);
    }
}
