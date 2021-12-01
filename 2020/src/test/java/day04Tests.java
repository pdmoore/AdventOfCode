import com.pdmoore.aoc.PuzzleInput;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class day04Tests {
    /*


byr (Birth Year)
iyr (Issue Year)
eyr (Expiration Year)
hgt (Height)
hcl (Hair Color)
ecl (Eye Color)
pid (Passport ID)
cid (Country ID)

Here is an example batch file containing four passports:

ecl:gry pid:860033327 eyr:2020 hcl:#fffffd
byr:1937 iyr:2017 cid:147 hgt:183cm

iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884
hcl:#cfa07d byr:1929

hcl:#ae17e1 iyr:2013
eyr:2024
ecl:brn pid:760753108 byr:1931
hgt:179cm

hcl:#cfa07d eyr:2025 pid:166559648
iyr:2011 ecl:brn hgt:59in

The first passport is valid - all eight fields are present. The second passport is invalid - it is missing hgt (the Height field).

The third passport is interesting; the only missing field is cid, so it looks like data from North Pole Credentials, not a passport at all!
Surely, nobody would mind if you made the system temporarily ignore missing cid fields. Treat this "passport" as valid.

The fourth passport is missing two fields, cid and byr. Missing cid is fine, but missing any other field is not, so this passport is invalid.

..part 1 has 8 keys, or 7 keys but not the CID
invalid otherwise

     */


    @Test
    public void part1_validPassport_Has8Keys() {
        List<String> input = new ArrayList<>();
        input.add("ecl:gry pid:860033327 eyr:2020 hcl:#fffffd");
        input.add("byr:1937 iyr:2017 cid:147 hgt:183cm");
        input.add("");

        String passportString = passportOnOneLine(input);
        Passport p = new Passport(passportString);

        assertTrue(p.IsValid());
    }

    @Test
    public void part1_invalidPassport() {
        List<String> input = new ArrayList<>();
        input.add("iyr:2013 ecl:amb cid:350 eyr:2023 pid:028048884");
        input.add("hcl:#cfa07d byr:1929");
        input.add("");

        String passportString = passportOnOneLine(input);
        Passport p = new Passport(passportString);

        assertFalse(p.IsValid());
    }

    @Test
    public void part1_validPassport_OnlyMissingCID() {
        List<String> input = new ArrayList<>();
        input.add("hcl:#ae17e1 iyr:2013");
        input.add("eyr:2024");
        input.add("ecl:brn pid:760753108 byr:1931");
        input.add("hgt:179cm");
        input.add("");

        String passportString = passportOnOneLine(input);
        Passport p = new Passport(passportString);

        assertTrue(p.IsValid());
    }

    @Test
    public void part1_invalid_TooFewFields() {
        List<String> input = new ArrayList<>();
        input.add("hcl:#cfa07d eyr:2025 pid:166559648");
        input.add("iyr:2011 ecl:brn hgt:59in");
        input.add("");

        String passportString = passportOnOneLine(input);
        Passport p = new Passport(passportString);

        assertFalse(p.IsValid());
    }

    @Test
    public void part1_solution() {
        List<String> rawInput = PuzzleInput.asListOfStringsFrom("./data/day04-part01");

        List<Passport> passports = convertInputToPassports(rawInput);

        int validCount = 0;
        for (Passport p:
                passports) {
            if (p.IsValid()) {
                validCount++;
            }
        }
        assertEquals(279, passports.size());
//        assertEquals(213, validCount);  // valid passports without field validation
        assertEquals(147, validCount);
    }

    private List<Passport> convertInputToPassports(List<String> input) {
        List<Passport> passports = new ArrayList<>();

        String passportLine = "";
        for (String line :
                input) {

            if (line.isEmpty()) {
                passports.add(new Passport(passportLine));
                passportLine = "";
            } else {
                passportLine += line;
                passportLine += " ";
            }
        }

        if (!passportLine.isEmpty()) {
            passports.add(new Passport(passportLine));
        }

        return passports;
    }


    private String passportOnOneLine(List<String> input) {
        String result = "";
        for (String line :
                input) {
            if (!line.isEmpty()) {
                result += line;
                result += " ";
            }
        }
        return result;
    }

    private class Passport {
        private final String passportString;

        public Passport(String passportString) {
            this.passportString = passportString;
        }

        public boolean IsValid() {
            String[] keyPairs = passportString.split(" ");
            if (keyPairs.length == 8) {
                return validateData(keyPairs);
            }

            if (keyPairs.length == 7) {
                for (String keyPair :
                        keyPairs) {
                    if (keyPair.contains("cid")) {
                        return false;
                    }
                }
                return validateData(keyPairs);
            }

            return false;
        }

        private boolean validateData(String[] keyPairs) {
            for (int i = 0; i < keyPairs.length; i++) {
                String[] field = keyPairs[i].split(":");
                switch (field[0]) {
                    case "byr": if (!validateBirthYear(field[1])) return false; break;
                    case "iyr": if (!validateIssueYear(field[1])) return false; break;
                    case "eyr": if (!validateExpirationYear(field[1])) return false; break;
                    case "hgt": if (!validateHeight(field[1])) return false; break;
                    case "hcl": if (!validateHairColor(field[1])) return false; break;
                    case "ecl": if (!validateEyeColor(field[1])) return false; break;
                    case "pid": if (!validatePassportID(field[1])) return false; break;
                }
            }

            return true;
        }

        private boolean validateBirthYear(String byr) {
            int year = Integer.parseInt(byr);
            return year >= 1920 && year <= 2002;
        }

        private boolean validateIssueYear(String iyr) {
            int year = Integer.parseInt(iyr);
            return year >= 2010 && year <= 2020;
        }

        private boolean validateExpirationYear(String eyr) {
            int year = Integer.parseInt(eyr);
            return year >= 2020 && year <= 2030;
        }

        private boolean validateHeight(String hgt) {
            if (hgt.endsWith("cm")) {
                int height = Integer.parseInt((hgt.substring(0, hgt.length() - 2)));
                return height >= 150 && height <= 193;
            } else if (hgt.endsWith("in")) {
                int height = Integer.parseInt((hgt.substring(0, hgt.length() - 2)));
                return height >= 59 && height <= 76;
            }
            return false;
        }

        private boolean validateHairColor(String hcl) {
            if (hcl.charAt(0) != '#' || hcl.length() != 7) return false;

            // all of the input passed this check
            for (int i = 1; i < hcl.length(); i++) {
                if (Character.digit(hcl.charAt(i), 16) == -1) {
                    return false;
                }
            }

            return true;
        }

        private boolean validateEyeColor(String ecl) {
            List<String> validEyeColors = new ArrayList<>();
            validEyeColors.add("amb");
            validEyeColors.add("blu");
            validEyeColors.add("brn");
            validEyeColors.add("gry");
            validEyeColors.add("grn");
            validEyeColors.add("hzl");
            validEyeColors.add("oth");

            return validEyeColors.contains(ecl);
        }

        private boolean validatePassportID(String pid) {
            if (pid.length() != 9) return false;
            String regex = "[0-9]+";
            return pid.matches(regex);
        }
    }
}
