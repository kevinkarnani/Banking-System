package banking;

public class PassTimeValidator extends Validator {
    public PassTimeValidator() {
    }

    @Override
    public boolean validate(String command) {
        command = command.toLowerCase();
        String[] words = command.split("\\s+");
        return words.length == 2 && words[0].equals("pass") && words[1].matches("\\d+") &&
                Integer.parseInt(words[1]) >= 1 && Integer.parseInt(words[1]) <= 60;
    }
}
