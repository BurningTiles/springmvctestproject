window.onload = function() {
	getPageHits();
	setInterval(getPageHits, 3 * 1000);
	let search = document.getElementById("search");
	if(search){
		search.addEventListener("input", changeHandle);
		if(search.value.length)
			document.getElementById("clear").hidden=false;
	}
}

function changeHandle(e) {
	// console.log("changes");
	let clr = document.getElementById("clear");
	if(clr){
		if(e.target.value.length)
			clr.hidden = false;
		else
			clr.hidden = true;
	}
}

function getPageHits() {
	var path = window.location.pathname;
	if (path === "/" || path === "/index")
		path = "/home";
	if (path.startsWith("/edit"))
		path = "/edit";
	var pageHits = document.getElementById("pageHits");
	if (pageHits) {
		fetch("/hits" + path)
			.then(res => res.json())
			.then(body => {
				pageHits.innerText = body;
			})
			.catch(err => {
				pageHits.innerText = 0;
			}
			);
	}
}