function askConfirm(msg, url, confirmLabel, cancelLabel) {
	bootbox.confirm({
		message : '<div class="text-center">' + msg + '</div>',
		buttons : {
			confirm : {
				label : confirmLabel,
				className : 'btn btn-danger'
			},
			cancel : {
				label : cancelLabel,
				className : 'btn btn-default'
			}
		},
		callback : function(result) {
			if (result != null && result) {
				window.location.replace(url);
			}
		}
	});
}

function relativeRedir(loc) {
	var b = document.getElementsByTagName('base');
	if (b && b[0] && b[0].href) {
		if (b[0].href.substr(b[0].href.length - 1) == '/' && loc.charAt(0) == '/')
			loc = loc.substr(1);
		loc = b[0].href + loc;
	}
	window.location.replace(loc);
}

function submitSimpleBet(marketId, matchId) {
	var quantity = document.getElementById('input' + marketId).value;
	var url = '/bet/simpleBet.do?marketId=' + marketId + '&matchId=' + matchId + '&quantity=' + quantity;

	relativeRedir(url);
}

function submitSelectedSimpleBet(marketId, matchId, betId) {
	var quantity = document.getElementById('input' + betId).value;
	var url = '/bet/simpleBet.do?marketId=' + marketId + '&matchId=' + matchId + '&quantity=' + quantity + '&betId=' + betId;

	relativeRedir(url);
}

function submitMultipleBets() {
	var quantity = document.getElementById('inputMultiple').value;
	var inputs = document.getElementsByTagName("input");
	var url;
	var bets = '';

	for ( var i = 0; i < inputs.length; i++) {
		if (inputs[i].type == "checkbox" && inputs[i].checked) {
			bets += inputs[i].id.substring(5, inputs[i].id.length) + ',';
		}
	}

	if (bets.length > 0) {
		bets = bets.substring(0, bets.length - 1);
	}

	url = '/bet/multipleBet.do?betsIdsStr=' + bets + '&quantity=' + quantity;

	relativeRedir(url);
}
