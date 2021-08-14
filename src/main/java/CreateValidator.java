import java.util.ArrayList;

public class CreateValidator {
    public Bank bank;

    public CreateValidator(Bank bank) {
        this.bank = bank;
    }

    public boolean validate(String command) {
        int len = 4;
        command = command.toLowerCase();
        String[] words = command.split("\\s+");
        ArrayList<String> accountTypes = new ArrayList<>();

        accountTypes.add("savings");
        accountTypes.add("checking");
        accountTypes.add("cd");

        if (command.contains("cd")) {
            len = 5;
        }

        boolean flag = words.length == len && words[0].equals("create") && accountTypes.contains(words[1]) && words[2]
                .matches("\\d+") && words[3].matches("\\d*\\.?\\d+") && words[2].length() == 8 &&
                !this.bank.accountExists(Integer.parseInt(words[2])) && Double.parseDouble(words[3]) <= 10 && Double.parseDouble
                (words[3]) >= 0;

        if (len == 5) {
            flag = flag && words[4].matches("\\d*\\.?\\d+") && Float.parseFloat(words[4]) <= 10000 && Double.parseDouble
                    (words[4]) >= 1000;
        }

        return flag;
    }
}
