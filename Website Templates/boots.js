$(document).ready(function() {
	$("#done").click(function() {
			var order ={
				    weatherUpdateRest: $("#fcast").val(),
				    cityRest: $("#city").val(),
                };
			
			$.ajax({
                	type: 'POST',
                	url: 'http://weather-api.sulekha.rocks:8080/realtimeWeatherUpdate/post-weather-update',
                	data: JSON.stringify(order),
                	contentType: "application/json",
                	success: function(response){
                		alert('Successfully uploaded forecast info');
                		console.log(response);
                	},
                	error: function(response){
                		alert('We are not able to satisfy your request at this time');
                		console.log(response);
                	}
                });
});
});