package quest.constant;

public final class QuizConstant {
    public static final int FIRST_QUESTION_ID = 1;
    public static final String QUIZ_PAGE_JSP = "jsp/quizPage.jsp";
    public static final String QUIZ_FINISHED_JSP = "jsp/quizFinished.jsp";
    public static final String FAIL_PAGE_JSP = "jsp/failPage.jsp";
    public static final String CURRENT_QUESTION_ID_ATTRIBUTE = "currentQuestionId";

    private QuizConstant() {
        throw new UnsupportedOperationException("This class should not be instantiated.");
    }
}

