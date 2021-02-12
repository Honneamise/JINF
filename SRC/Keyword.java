class Keyword 
{
    public static final int UNKNOW      = -1;
    public static final int IO          = 0;
    public static final int LOGIC       = 1;
    public static final int MATH        = 2;
    public static final int STACK       = 3;
    public static final int STATEMENT   = 4;
    public static final int UTIL        = 5;
    

    public static boolean isDefined(String s)
    {
        return KeywordIO.isDefined(s) ||
                KeywordLogic.isDefined(s) ||
                KeywordMath.isDefined(s) ||
                KeywordStack.isDefined(s) ||
                KeywordStatement.isDefined(s) ||
                KeywordUtil.isDefined(s);
    }

    public static int type(String s)
    {
        if (KeywordIO.isDefined(s)) return IO;
        if (KeywordLogic.isDefined(s)) return LOGIC;
        if (KeywordMath.isDefined(s)) return MATH;
        if (KeywordStack.isDefined(s)) return STACK;
        if (KeywordStatement.isDefined(s)) return STATEMENT;
        if (KeywordUtil.isDefined(s)) return UTIL;

        return UNKNOW;
    }

    public static void execute(Interpreter intp, Lexer lexer, String token)
    {
        int type = Keyword.type(token);

        switch (type)
        {
            case IO : KeywordIO.execute(intp, lexer, token); break;
            case LOGIC : KeywordLogic.execute(intp, lexer, token); break;
            case MATH : KeywordMath.execute(intp, lexer, token); break;
            case STACK : KeywordStack.execute(intp, lexer, token); break;
            case STATEMENT : KeywordStatement.execute(intp, lexer, token); break;
            case UTIL : KeywordUtil.execute(intp, lexer, token); break;

            default : intp.setError("Unknow keyword type");
        }

    }
}
