$(document).ready(function() {
	$("#register").click(function() {
			var order ={
                		name: {
                			firstName: $("#name").val(),
                			middleName: null,
                			lastName: null,
                		},
                		email: $("#email").val(),
                		addresses: {
                			HomeAddress: {
                				street:  $("#city").val(),
                				apartment: null,
                				city: null,
                				state: null,
                				zipCode: null,
                				phoneNumber: null,
                				country: null,
                				locationCoordinatesRest: {
                					longitude: null,
                					latitude: null,
                				}
                			},
            				WorkAddress: {
            					street: null,
            					apartment: null,
            					city: null,
            					state: null,
            					zipCode: null,
            					phoneNumber: null,
            					country: null,
            					locationCoordinatesRest: {
            						longitude: null,
            						latitude: null,
            					}
            				}	
                		},
                		//favouriteCities: [null],
                		//profilePhoto: $("#profilepicture").val(),
                };
			
			$.ajax({
                	type: 'POST',
                	url: 'http://weather-api.sulekha.rocks:8080/userprofile/create-user-profile',
                	data: JSON.stringify(order),
                	contentType: "application/json",
                	success: function(response){
                		alert('successfully created user profile');
                		console.log(response);
                	},
                	error: function(response){
                		alert('Email Id already exists');
                		console.log(response);
                	}
                });
});
});