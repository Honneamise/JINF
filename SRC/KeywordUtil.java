import java.util.*;

class KeywordUtil
{
    public static String[] list = {"FUNC","VAR", "#"};

    public static boolean isDefined(String s)
    {
        return Arrays.asList(list).contains(s);
    }

    public static void execute(Interpreter intp, Lexer lexer, String token)
    {
        switch(token)
        {
            case "FUNC":
                {
                    String func_name = lexer.get();

                    String ins = intp.functions.getOrDefault(func_name, null);

                    if(ins==null) { intp.setError("The function : \""+ func_name +"\" does not exists"); return;}
                    
                    intp.out+="( " + ins + " )";
                }
                break;

            case "VAR":
                {
                    String var_name = lexer.get();

                    Integer n = intp.variables.getOrDefault(var_name, null);

                    if(n==null) { intp.setError("The variable : \""+ var_name +"\" does not exists"); return;}
                    
                    intp.out+="( " + n + " )";
                }
                break;

            case "#":
                {
                    lexer.getUntilEnd();
                }
                break;

            default: intp.setError("Unknow KeywordUtil"); break;

        }
    }
}
