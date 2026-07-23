package com.ex3.khg.review.exception;

public enum ReviewException {
    REVIEW_NOT_REGISTERED("Review Not Registere", 400),
    REVIEW_PRODUCT_NOT_FOUND("Product Not Found for Review", 404);

    private final ReviewTaskException reviewTaskException;

    ReviewException(String msg, int code) {
        reviewTaskException = new ReviewTaskException(msg, code);
    }

    public ReviewTaskException get() {
        return reviewTaskException;
    }
}
