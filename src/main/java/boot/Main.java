package boot;


import java.util.Arrays;
import java.util.List;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class Main
{
    private static final Logger log = Logger.getLogger(Main.class.getName());

    public static void main(String[] args)
    {
        log.info("Hello world!");

        List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
        log.info(list.toString() + list.get(0).getClass());

        List<String> listString = list.stream().filter((n) -> (n % 2) != 0).map((n) ->
                n.toString()).collect(Collectors.toList());

        log.info(listString.toString()  + listString.get(0).getClass());

    }

}
