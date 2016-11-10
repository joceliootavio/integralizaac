var statusDlgTimer = null;

function showStatusDialog() {
	if (statusDlgTimer === null) {
		statusDlgTimer = setTimeout(function() {
			statusDialog.show()
		}, 600);
	}
}
function hideStatusDialog() {
	if (statusDlgTimer !== null) {
		clearTimeout(statusDlgTimer);
		statusDialog.hide();
		statusDlgTimer = null;
	}
}

function validateNumber(event) {
    var key = window.event ? event.keyCode : event.which;

    if (event.keyCode == 8) {
        return true;
    }
    else if ( key < 48 || key > 57 ) {
        return false;
    }
    else return true;
}

function callbackSolicitarAvaliacao(args) {
	$('#msgSolicitarAvaliacao').html(args.mensagem);
	alertaCreditosDialogW.show();
}