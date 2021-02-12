import java.util.*;

class KeywordIO
{
    public static String[] list = {"^", "CR", "SPACE", "\"" };

    public static boolean isDefined(String s)
    {
        return Arrays.asList(list).contains(s);
    }

    public static void execute(Interpreter intp, Lexer lexer, String token)
    {
        switch(token)
        {
            case "^":
                {
                    if(intp.stack.empty()){ intp.setError("Stack empty"); return;}

                    int n = intp.stack.pop();

                    intp.out+=n+"";
                }
                break;

            case "CR":
                {
                    intp.out+="\n";
                }
                break;

            case "SPACE":
                {
                    intp.out+=" ";
                }
                break;

            case "\"":
                {
                    String s = lexer.getUntil("\"");

                    if(s != null) { intp.out+= s; }
                    else { intp.setError("Error reading string (missing \" ?)"); }   
                }
                break;

            default: intp.setError("Unknow KeywordIO"); break;
        }
    }
}