package bash.hong_heiat;

import bash.hong_heiat.lexer.Lexer;
import bash.hong_heiat.parser.Parser;

import java.io.File;
import java.nio.file.Files;
import java.util.stream.Collectors;

public class Main {
    public static void main(String[] args) throws Exception {
        Lexer lexer = new Lexer();
        var lines = Files.readAllLines(new File("./test/example.preq").toPath()).stream().collect(Collectors.joining(""));
        var tokens = lexer.tokenize(lines);
        Parser parser = new Parser();
        tokens = parser.sort(tokens);
        for (var token : tokens) {
            System.out.println(token);
        }
//        System.out.println("OK");
//        parser.execute(tokens);
//        for (var token : parser.getMaster().getVariables().values()) {
//            System.out.println(token);
//        }
    }
}
