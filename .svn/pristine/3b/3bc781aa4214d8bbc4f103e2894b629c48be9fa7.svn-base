function fileQueueError(file, errorCode, message) {
	try {
		var errorName = "";
		switch (errorCode) {
		case SWFUpload.QUEUE_ERROR.QUEUE_LIMIT_EXCEEDED:
			errorName = "超出列队数量限制";
			alert(errorName);
			break;
		case SWFUpload.QUEUE_ERROR.ZERO_BYTE_FILE:
			errorName = "文件大小为0kb，无法上传！";
			alert("[" + file.name + "]" + errorName);
			break;
		case SWFUpload.QUEUE_ERROR.FILE_EXCEEDS_SIZE_LIMIT:
			errorName = "超过文件大小限制";
			alert(file.name + ":" + errorName);
			break;
		case SWFUpload.QUEUE_ERROR.INVALID_FILETYPE:
		default:
			alert(errorCode);
			break;
		}

	} catch (ex) {
		this.debug(ex);
	}
}

/**
 * 当文件选择对话框关闭消失时，如果选择的文件成功加入上传队列， 那么针对每个成功加入的文件都会触发一次该事件（N个文件成功加入队列，就触发N次此事件）。
 * 
 * @param {}
 *            file id : string, // SWFUpload控制的文件的id,通过指定该id可启动此文件的上传、退出上传等
 *            index : number, // 文件在选定文件队列（包括出错、退出、排队的文件）中的索引，getFile可使用此索引 name :
 *            string, // 文件名，不包括文件的路径。 size : number, // 文件字节数 type : string, //
 *            客户端操作系统设置的文件类型 creationdate : Date, // 文件的创建时间 modificationdate :
 *            Date, // 文件的最后修改时间 filestatus : number //
 *            文件的当前状态，对应的状态代码可查看SWFUpload.FILE_STATUS }
 */
function fileQueued(file) {
	var filesize;
	if (file.size < 1024)
		filesize = file.size + "字节";
	else if (file.size < 1048576)
		filesize = Math.round(file.size / 1024) + "K";
	else {
		filesize = Math.round(file.size / 1024 / 1024) + "M";
	}
	data.rows.push({
		id : file.id,
		name : file.name,
		size : filesize,
		state : "列队"
	});
	data.total++;
	$("#report").datagrid("loadData", data);
}

function startUpload() {
	if (data.total > 0) {
		data.rows[0].state = "上传中";
		$("#report").datagrid("loadData", data);
	} else {
		alert("请先选择文件");
	}
	swfu.startUpload();
}
function fileDialogComplete(numFilesSelected, numFilesQueued) {
}
// 上传完成,等待服务器处理
function uploadProgress(file, bytesLoaded) {
	try {
		var percent = Math.round((bytesLoaded / file.size) * 100);
		if (percent == 100) {
			$('#p').progressbar({
				value : 100,
				text : "上传完成,等待服务器处理"
			});
		} else {
			$('#p').progressbar({
				value : percent,
				text : "正在上传..." + percent + "%"
			});
		}
	} catch (ex) {
		this.debug(ex);
	}
}
// 单个文件上传完成,服务器处理完成
function uploadSuccess(file, serverData) {
	try {
		data.rows.shift();
		data.total--;
		$("#report").datagrid("loadData", data);

		$('#p').progressbar({
			text : "文件上传完成"
		});
	} catch (ex) {
		this.debug(ex);
	}
}

// id : string, // SWFUpload file id, used for starting or cancelling and upload
// index : number, // The index of this file for use in getFile(i)
// name : string, // The file name. The path is not included.
// size : number, // The file size in bytes
// type : string, // The file type as reported by the client operating system
// creationdate : Date, // The date the file was created
// modificationdate : Date, // The date the file was last modified
// filestatus : number, // The file's current status. Use SWFUpload.FILE_STATUS
// to interpret the value.

function cancelUpload() {
	var infoTable = document.getElementById("infoTable");
	var rows = infoTable.rows;
	var ids = new Array();
	var row;
	if (rows == null) {
		return false;
	}
	for ( var i = 0; i < rows.length; i++) {
		ids[i] = rows[i].id;
	}
	for ( var i = 0; i < ids.length; i++) {
		deleteFile(ids[i]);
	}
}

function deleteFile(fileid) {
	for ( var i = 0; i < data.rows.length; i++) {
		if (data.rows[i].id == fileid) {
			data.rows.splice(i, 1);
			break;
		}
	}
	$("#report").datagrid("loadData", data);
}

// 服务器处理完毕
function uploadComplete(file) {
	try {
		// if (this.getStats().files_queued > 0) {
		if (data.total > 0) {
			startUpload();
		} else {
			$('#p').progressbar({
				text : "所有文件上传完成"
			});
			$("#uploaddialog").parents("div[id]").dialog("close");
		}
	} catch (ex) {
		this.debug(ex);
	}
}

function uploadError(file, errorCode, message) {
	var imageName = "<font color='red'>文件上传出错!</font>";
	var progress;
	try {
		switch (errorCode) {
		case SWFUpload.UPLOAD_ERROR.FILE_CANCELLED:
			try {
				progress = new FileProgress(file,
						this.customSettings.upload_target);
				progress.setCancelled();
				progress.setStatus("<font color='red'>取消上传!</font>");
				progress.toggleCancel(false);
			} catch (ex1) {
				this.debug(ex1);
			}
			break;
		case SWFUpload.UPLOAD_ERROR.UPLOAD_STOPPED:
			try {
				progress = new FileProgress(file,
						this.customSettings.upload_target);
				progress.setCancelled();
				progress.setStatus("<font color='red'>停止上传!</font>");
				progress.toggleCancel(true);
			} catch (ex2) {
				this.debug(ex2);
			}
		case SWFUpload.UPLOAD_ERROR.UPLOAD_LIMIT_EXCEEDED:
			imageName = "<font color='red'>文件大小超过限制!</font>";
			break;
		default:
			alert(message);
			break;
		}
		addFileInfo(file.id, imageName);
	} catch (ex3) {
		this.debug(ex3);
	}

}
