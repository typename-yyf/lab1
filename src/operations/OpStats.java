package operations;

import log.Loggers;
import utils.Parser;

import java.io.IOException;

public class OpStats extends Operation{
    String option;
    @Override
    public void parseArgument(Parser parser) {
        option = parser.getAll();
        String [] words = option.split(" ");
        if(words.length==1){
            option = words[0];}
        else{
            option = words[1];
        }
    }

    @Override
    public void _execute() throws IOException {
       textWorkload.stats(option);
    }
}
