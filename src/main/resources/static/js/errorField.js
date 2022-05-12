function markingErrorField (response) {
    const parsedResponse = response.responseJSON;
    console.log(JSON.stringify(parsedResponse));
    const errorFields = parsedResponse.error.errorDetails;

    const list = document.getElementsByClassName('error-message');

    if (list.length > 0) {
        $('.error-message').remove();
    }

    if (!errorFields) {
        alert(parsedResponse.message);
        return;
    }

    let $field, error;
    for (let i = 0; i < errorFields.length; i++) {
        error = errorFields[i];
        $field = $('#' + error['field']);

        if($field && $field.length > 0){
            if (error === 'globalError') {
                $field.append('<div class="error-message text-small text-danger">' + error.defaultMessage + '</div>');
            } else {
                $field.after('<div class="error-message text-muted text-small text-danger">' + error.defaultMessage + '</div>');
            }
        }
    }
}