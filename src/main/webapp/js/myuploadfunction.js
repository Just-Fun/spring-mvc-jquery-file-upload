$(function () {
    $('#fileupload').fileupload({
        dataType: 'json',

        done: function (e, data) {
//            $('#uploaded-files tr').slice(1).remove();
        	$("tr:has(td)").remove();
            $.each(data.result, function (index, file) {

                $("#uploaded-files").append(
                		$('<tr/>')
                		.append($('<td/>').text(file.fileName))
                		.append($('<td/>').text(file.fileSize))
                		.append($('<td/>').text(file.fileType))

//                		.append($('<td/>').html("<a href='rest/controller/get/"+index+"'>Click</a>"))
                		)//end $("#uploaded-files").append()
                $("#getResult").show();
            });
        },
        
        progressall: function (e, data) {
	        var progress = parseInt(data.loaded / data.total * 100, 10);
	        $('#progress .bar').css(
	            'width',
	            progress + '%'
	        );
   		},
   		
		dropZone: $('#dropzone')
    });
});