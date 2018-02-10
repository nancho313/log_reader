var scrollLog = true;

$(document).ready(function(){
	
	$("#button_criteria").click(send_criteria);
	
	$(".log_toggle_button").click(changeToggleButtons);

	$("#clean_log").click(cleanLogReader);

	$("#close_log").click(closeLogReader);

	$('#log_reader').scroll(scrollLogPanel);

	init_web_socket();
});

function init_web_socket(){
	
	if ("WebSocket" in window)
	{  

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

	sshConfigManagerDTO.messageType = "REQUEST_LOG";
	
	sshConfigManagerDTO.ip = $("#log_ip").val();
	
	sshConfigManagerDTO.user = $("#log_user").val();
	
	sshConfigManagerDTO.password = $("#log_password").val();
	
	sshConfigManagerDTO.port = $("#log_port").val();
	
	sshConfigManagerDTO.dirLog = $("#log_dir_file").val();
	
	var userCriteriaDTO = {};
	
	userCriteriaDTO.resultType = $(".selected_log_toggle_button").attr("log_val");

	var textToSearch = $("#log_text_to_search").val();

	userCriteriaDTO.matches = [];

	if(textToSearch !== null && textToSearch.trim().length > 0){

	    userCriteriaDTO.matches = [$("#log_text_to_search").val()];
	}

	userCriteriaDTO.criterias = [];
	
	if($("#log_invert").is(":checked")){

	    var criteria = {};

	    criteria.criteria = "INVERT";

	    userCriteriaDTO.criterias.push(criteria);
	}

	if($("#log_ignore_case").is(":checked")){

	    var criteria = {};

    	criteria.criteria = "IGNORE_CASE";

        userCriteriaDTO.criterias.push(criteria);
    }

    if($("#log_count_matches").is(":checked")){

        var criteria = {};

        criteria.criteria = "COUNT_MATCHES";

        userCriteriaDTO.criterias.push(criteria);
    }

    if($("#log_line_number").is(":checked")){

        var criteria = {};

        criteria.criteria = "LINE_NUMBER";

        userCriteriaDTO.criterias.push(criteria);
    }

    if($("#log_max_count").val().trim().length > 0){

        var criteria = {};

        criteria.criteria = "MAX_COUNT";

        criteria.value = $("#log_max_count").val().trim();

        userCriteriaDTO.criterias.push(criteria);
    }

    if($("#log_before").val().trim().length > 0){

        var criteria = {};

        criteria.criteria = "BEFORE_CONTEXT";

        criteria.value = $("#log_before").val().trim();

        userCriteriaDTO.criterias.push(criteria);
    }

    if($("#log_after").val().trim().length > 0){

        var criteria = {};

        criteria.criteria = "AFTER_CONTEXT";

        criteria.value = $("#log_after").val().trim();

        userCriteriaDTO.criterias.push(criteria);
    }

    if($("#log_context").val().trim().length > 0){

        var criteria = {};

        criteria.criteria = "CONTEXT";

        criteria.value = $("#log_context").val().trim();

        userCriteriaDTO.criterias.push(criteria);
    }
	
	sshConfigManagerDTO.userCriteriaDTO = userCriteriaDTO;
	
	log_reader_web_socket.send(JSON.stringify(sshConfigManagerDTO));
}

function load_response(evt){
	
	var received_msg = JSON.parse(evt.data);

	var parsedContentRead = received_msg.contentRead;
	
	$("#log_reader").append(parsedContentRead);

	if(scrollLog){

	    $('#log_reader').scrollTop($('#log_reader')[0].scrollHeight);
	}
}

function scrollLogPanel(){

    var scrollPosition = $('#log_reader').scrollTop();

    var scrollHeight = $('#log_reader')[0].scrollHeight;
}

function cleanLogReader(){

    $("#log_reader").html("");
}

function closeLogReader(){

    var closeLog = {};

    closeLog.messageType = "CLOSE_LOG";

    log_reader_web_socket.send(JSON.stringify(closeLog));

    cleanLogReader();
}