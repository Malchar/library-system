document.getElementById("buttonAdvanced").onclick=function(){
	let x = document.getElementById("searchAdvanced").style.display;
	if(x === "none"){
		document.getElementById("searchAdvanced").style.display="";
	}
	else{
		document.getElementById("searchAdvanced").style.display="none";
	}
};