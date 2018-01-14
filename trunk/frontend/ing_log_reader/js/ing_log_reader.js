$(document).ready(function(){
	
	$("#button_criteria").click(send_criteria);
	
	$(".log_toggle_button").click(changeToggleButtons);

	init_web_socket();
});

function init_web_socket(){
	
	if ("WebSocket" in window)
	{  
	   // Let us open a web socket
	   log_reader_web_socket = new WebSocket("ws://localhost:8080/ing_log_reader_api/LogSender");
		
	   log_reader_web_socket.onopen = onOpenWS;	   
		
	   log_reader_web_socket.onmessage = load_response;	   
			
	   window.onbeforeunload = function(event) {
		  log_reader_web_socket.close();
	   };
	}
}

function onOpenWS(){
	
}

function changeToggleButtons(){
	
	$(".log_toggle_button").removeClass("selected_log_toggle_button");
	
	$(this).addClass("selected_log_toggle_button");
}

function send_criteria(){
	
	var sshConfigManagerDTO = {};
	
	sshConfigManagerDTO.ip = $("#log_ip").val();
	
	sshConfigManagerDTO.user = $("#log_user").val();
	
	sshConfigManagerDTO.password = $("#log_password").val();
	
	sshConfigManagerDTO.port = $("#log_port").val();
	
	sshConfigManagerDTO.dirLog = $("#log_dir_file").val();
	
	var userCriteriaDTO = {};
	
	userCriteriaDTO.resultType = $(".selected_log_toggle_button").attr("log_val");
	
	userCriteriaDTO.matches = [$("#log_text_to_search").val()];
	
	//userCriteriaDTO.criterias = [];
	
	sshConfigManagerDTO.userCriteriaDTO = userCriteriaDTO;
	
	console.log(JSON.stringify(sshConfigManagerDTO));
	
	log_reader_web_socket.send(JSON.stringify(sshConfigManagerDTO));
}

function load_response(evt){
	
	var received_msg = evt.data;
	
	$("#log_reader").append(received_msg);
	
	console.log(evt.data);  
}