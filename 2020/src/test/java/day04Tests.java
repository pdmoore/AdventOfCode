import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

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


List<String> slope = Utilities.fileToStringList("./data/day04-part01");

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
            return keyPairs.length == 8;
        }
    }
}
