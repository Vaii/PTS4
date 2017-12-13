package models.storage;

/**
 * The status enum contains all possible statuses a tuitionform can have.
 * <p>
 * Approved being: the form has been approved and the applicant has been added to the training.
 * Pending: The employee has filled out the form and it has been sent to the manager for approval.
 * Declined: The manager has declined the request and the employee has not been added to the training.
 */
public enum Status {
    GOEDGEKEURD,
    PENDING,
    AFGEWEZEN
}
