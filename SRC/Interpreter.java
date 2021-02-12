import java.util.*;

class Interpreter 
{
    //token types
    public static final int UNKNOW      = -1;
    public static final int EMPTY       = 0;
    public static final int NUMBER      = 1;
    public static final int KEYWORD     = 2;
    public static final int FUNCTION    = 3;
    public static final int VARIABLE    = 4;

    String out = "";
    String err = null;

    Stack<Integer> stack = null;
    HashMap<String, String> functions = null;
    HashMap<String, Integer> variables = null;

    Interpreter()
    {
        stack = new Stack<Integer>();
        functions = new HashMap<String, String>();
        variables = new HashMap<String, Integer>();
    }

    public void clearIO()
    {
        out = "";
        err = null;
    }

    public void reset()
    {
        stack = new Stack<Integer>();
        functions = new HashMap<String, String>();
        variables = new HashMap<String, Integer>();
    }

    public boolean functionDefined(String func_name)
    {
        return functions.getOrDefault(func_name,null)!=null ;
    }

    public boolean variableDefined(String var_name)
    {
        return variables.getOrDefault(var_name,null)!=null ;
    }

    public int tokenType(String token)
    {
        //empty
        if(token.length()==0){ return EMPTY; }
        
        //number
        if( token.matches("[+-]?+\\d+$")) { return NUMBER; };

        //keyword
        if(Keyword.isDefined(token)) { return KEYWORD; }

        //function
        if( functionDefined(token)  ) { return FUNCTION; };

        //variable
        if( variableDefined(token)  ) { return VARIABLE; };

        //unknow
        return UNKNOW;
    }

    public void setError(String s)
    {
        err = s;
    }

    public void execute(Lexer lexer)
    {
        while(!lexer.empty() && err==null )
        {
            String token = lexer.get();
 
            int type = tokenType(token);

            switch(type)
            {
                case EMPTY:
                    break;

                case NUMBER:
                    executeNumber(token);
                    break;

                case KEYWORD:
                    executeKeyword(this,lexer,token);
                    break;

                case FUNCTION:
                    executeFunction(token);
                    break;

                case VARIABLE:
                    break;

                default:
                    setError(token+ " ?");
                    break;                  
            }          
        }

    }

    public void executeNumber(String token)
    {
        try
        {
            int n = Integer.parseInt(token);

            stack.push(n);
        }catch(Exception e){ setError("Number format not valid");}
    }

    public void executeKeyword(Interpreter intp, Lexer lexer, String token)
    {
        Keyword.execute(this, lexer, token);
    }

    public void executeFunction(String token)
    { 
        String ins = functions.getOrDefault(token,null);

        execute(new Lexer(ins));
        
        if(err!=null){ err = token + " -> " + err; }
    }


}
