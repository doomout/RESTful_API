package com.ex3.khg.review.exception;

public enum ReviewException {
    REVIEW_NOT_REGISTERED("Review Not Registere", 400),
    REVIEW_PRODUCT_NOT_FOUND("Product Not Found for Review", 404),
    REVIEW_NOT_FOUND("Revidw Not Found", 404),
    REVIEW_NOT_MODIFIED("Review Not modified", 400),
    REVIEW_NOT_REMOVED("Review Not Removed", 400),
    REVIEW_MISMATCH("Reviewer Mismatch", 400),
    REVIEW_NOT_MATCHED("Review Not Matched", 404);

    private final ReviewTaskException reviewTaskException;

    ReviewException(String msg, int code) {
        reviewTaskException = new ReviewTaskException(msg, code);
    }

    public ReviewTaskException get() {
        return reviewTaskException;
    }
}
