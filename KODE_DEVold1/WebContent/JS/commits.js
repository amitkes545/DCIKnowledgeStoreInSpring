function addCommas(e) {
	return String(e).replace(/(\d)(?=(\d{3})+$)/g, "$1,")
}
function jsonp(e, t) {
	var n = document.createElement("script");
	n.src = e + "?callback=callback00";
	n.async = true;
	if (t)
		n.onload = t;
	document.body.appendChild(n)
}
function callback00(e) {
	if (gType == "watch") {
		counter.innerHTML = addCommas(e.data.watchers);
		console.log("watchers", e.data.watchers)
	} else if (gType == "fork") {
		counter.innerHTML = addCommas(e.data.forks);
		console.log("forks", e.data.forks)
	} else if (gType == "follow") {
		counter.innerHTML = addCommas(e.data.followers);
		console.log("followers", e.data.followers)
	}
	counter.style.display = "block"
}
function gitinfo(e, t) {
	window.gType = e;
	mainButton = document.createElement("span");
	mainButton.className = "github-btn";
	button = document.createElement("a");
	button.target = "_blank";
	button.className = "gh-btn";
	mainButton.appendChild(button);
	var n = document.createElement("span");
	n.className = "gh-ico";
	button.appendChild(n);
	text = document.createElement("span");
	text.className = "gh-text";
	button.appendChild(text);
	counter = document.createElement("a");
	counter.target = "_blank";
	counter.className = "gh-count";
	counter.innerHTML = "+1K";
	mainButton.appendChild(counter);
	if (stargazers)
		stargazers.appendChild(mainButton);
	button.href = "https://github.com/" + user + "/" + repo + "/";
	if (gType == "watch") {
		mainButton.className += " github-watchers";
		text.innerHTML = "Star ";
		counter.href = "https://github.com/" + user + "/" + repo
				+ "/stargazers"
	} else if (gType == "fork") {
		mainButton.className += " github-forks";
		text.innerHTML = " Fork ";
		counter.href = "https://github.com/" + user + "/" + repo + "/network"
	} else if (gType == "follow") {
		mainButton.className += " github-me";
		text.innerHTML = "Follow @" + user;
		button.href = "https://github.com/" + user;
		counter.href = "https://github.com/" + user + "/followers"
	}
	if (gType == "follow") {
		jsonp("https://api.github.com/users/" + user, t)
	} else {
		jsonp("https://api.github.com/repos/" + user + "/" + repo, t)
	}
}
function issuesCallback(e) {
	githubIssues.innerHTML = "";
	e = e.data;
	var t = e.length;
	if (t > 10)
		t = 10;
	for ( var n = 0; n < t; n++) {
		var r = e[n];
		var i = document.createElement("div");
		i.className = "commit";
		var s = r.title;
		if (s.length > 50) {
			s = s.substr(0, 49) + "...";
			s = '<h2 title="' + r.title + '">' + s + "</h2><br />"
		} else
			s = "<h2>" + r.title + "</h2><br />";
		var o = r.body;
		o = o.replace(/</g, "&lt;").replace(/>/g, "&gt;");
		o = o.replace(urlRegex, shortenUrl).replace(/\n/g, "<br />");
		o = o.replace(/\n/g, "<br />");
		o = s + o;
		o = replaceMarkup(o);
		var u = document.createElement("div");
		u.className = "commit-desc";
		u.innerHTML = o;
		i.appendChild(u);
		var a = document.createElement("div");
		a.className = "commit-meta";
		var f = document.createElement("a");
		f.target = "_blank";
		f.href = r.html_url;
		f.className = "commit-url";
		f.innerHTML = r.comments + " Comments (Submitted "
				+ timeDifference(new Date, new Date(r.created_at)) + ")";
		a.appendChild(f);
		var l = document.createElement("div");
		l.className = "authorship";
		var c = new Image(24, 24);
		c.className = "gravatar";
		if (r.user && r.user.avatar_url)
			c.src = r.user.avatar_url;
		l.appendChild(c);
		var h = document.createElement("span");
		h.className = "author-name";
		h.innerHTML = '<a href="' + r.user.html_url
				+ '" rel="author" target="_blank">' + r.user.login + "</a>";
		l.appendChild(h);
		a.appendChild(l);
		i.appendChild(a);
		if (githubIssues)
			githubIssues.appendChild(i)
	}
}
function getLatestIssues(e) {
	if (!githubIssues) {
		if (e)
			e();
		return
	}
	var t = document.createElement("script");
	t.src = "https://api.github.com/repos/muaz-khan/" + repo
			+ "/issues?sha=master&callback=issuesCallback";
	t.async = true;
	if (e)
		t.onload = e;
	document.body.appendChild(t)
}
function commitsCallback(e) {
	githubCommits.innerHTML = "";
	e = e.data;
	var t = e.length;
	if (t > 15)
		t = 15;
	for ( var n = 0; n < t; n++) {
		var r = e[n];
		var i = document.createElement("div");
		i.className = "commit";
		var s = r.commit.message;
		s = s.replace(/</g, "&lt;").replace(/>/g, "&gt;");
		s = s.replace(urlRegex, shortenUrl).replace(/\n/g, "<br />");
		s = s.replace(/\n/g, "<br />");
		s = replaceMarkup(s);
		var o = document.createElement("div");
		o.className = "commit-desc";
		o.innerHTML = s;
		i.appendChild(o);
		var u = document.createElement("div");
		u.className = "commit-meta";
		var a = document.createElement("a");
		a.target = "_blank";
		a.href = r.html_url;
		a.className = "commit-url";
		a.innerHTML = timeDifference(new Date,
				new Date(r.commit.committer.date));
		u.appendChild(a);
		var f = document.createElement("div");
		f.className = "authorship";
		if (!r.author)
			r.author = "muaz-khan";
		var l = new Image(24, 24);
		l.className = "gravatar";
		l.src = r.author.avatar_url;
		if (!r.author || !r.author.avatar_url)
			l.src = "https://goo.gl/KaFpuL";
		f.appendChild(l);
		var c = document.createElement("span");
		c.className = "author-name";
		c.innerHTML = '<a href="'
				+ (r.author.html_url || "https://github.com/muaz-khan")
				+ '" rel="author" target="_blank">'
				+ (r.author.login || "Muaz Khan") + "</a>";
		f.appendChild(c);
		u.appendChild(f);
		i.appendChild(u);
		if (githubCommits)
			githubCommits.appendChild(i)
	}
}
function getLatestCommits(e) {
	var t = document.createElement("script");
	t.src = "https://api.github.com/repos/muaz-khan/" + repositorySelected
			+ "/commits?sha=master&callback=commitsCallback";
	t.async = true;
	if (!e && githubIssues)
		e = getLatestIssues;
	if (e)
		t.onload = e;
	document.body.appendChild(t)
}
function timeDifference(e, t) {
	var n = 60 * 1e3;
	var r = n * 60;
	var i = r * 24;
	var s = i * 30;
	var o = i * 365;
	var u = e - t;
	if (u < n) {
		return Math.round(u / 1e3) + " seconds ago"
	} else if (u < r) {
		return Math.round(u / n) + " minutes ago"
	} else if (u < i) {
		return Math.round(u / r) + " hours ago"
	} else if (u < s) {
		return Math.round(u / i) + " days ago"
	} else if (u < o) {
		return Math.round(u / s) + " months ago"
	} else {
		return Math.round(u / o) + " years ago"
	}
}
function replaceMarkup(e) {
	e = e.replace(/```javascript([^```]+)```|```html([^```]+)```/g,
			"<pre>$1</pre>");
	e = e.replace(/```JavaScript([^```]+)```|```html([^```]+)```/g,
			"<pre>$1</pre>");
	e = e.replace(/```js([^```]+)```|```html([^```]+)```/g, "<pre>$1</pre>");
	e = e.replace(/```([^```]+)```/g, "<pre>$1</pre>");
	e = e.replace(/``([^``]+)``/g, "<pre>$1</pre>");
	e = e.replace(/`([^`]+)`/g, "<code>$1</code>");
	e = e.replace(/\*\*([^\*\*]+)\*\*/g, "<strong>$1</strong>");
	e = e.replace(/#([0-9]+)/g, '<a href="https://github.com/muaz-khan/'
			+ repositorySelected + '/issues/$1" target="_blank">#$1</a>');
	e = e.replace(/```([^```]+)```/g, "<pre>$1</pre>");
	e = e.replace(/`([^`]+)`/g, "<code>$1</code>");
	return e
}
function getCommonjs() {
	var e = document.createElement("script");
	e.src = "//cdn.webrtc-experiment.com/common.js";
	e.async = true;
	document.body.appendChild(e)
}
var repositories = [ "WebRTC-Experiment", "RTCMultiConnection", "RecordRTC" ];
var repositoryIndex = Math.ceil(Math.random() * repositories.length);
var repositorySelected = repositories[repositoryIndex] || "WebRTC-Experiment";
var stargazers = document.querySelector(".github-stargazers");
var user = "muaz-khan", repo = "WebRTC-Experiment";
var mainButton, counter, text, button;
gitinfo("watch", function() {
	var e;
	if (githubCommits)
		e = getLatestCommits;
	else if (githubIssues)
		e = getLatestIssues;
	gitinfo("fork", function() {
		if (e)
			e(function() {
				gitinfo("follow", function() {
					if (e != getLatestIssues && githubIssues)
						getLatestIssues()
				})
			});
		else
			gitinfo("follow", function() {
				if (e != getLatestIssues && githubIssues)
					getLatestIssues()
			})
	})
});
var githubIssues = document.getElementById("github-issues");
if (githubIssues)
	githubIssues.innerHTML = '<div style="padding:1em .8em;">Getting latest issues...</div>';
var githubCommits = document.getElementById("github-commits");
if (githubCommits)
	githubCommits.innerHTML = '<div style="padding:1em .8em;">Getting latest commits...</div>';
var shortenUrl = function(e, t, n, r, i, s, o, u, a) {
	var f = 18;
	if (e.charAt(0) == "(" && e.charAt(e.length - 1) == ")") {
		e = e.slice(1, -1)
	}
	if (!t) {
		e = "http://" + e
	}
	var l = n.replace(/www\./gi, "");
	var c = l + (i || "") + (s || "") + (o || "") + (u || "") + (a || "");
	if (c.length > f && l.length < f) {
		c = c.slice(0, l.length + (f - l.length)) + "..."
	}
	return '<a href="'
			+ e
			+ '" target="_blank">'
			+ c.replace("webrtc-experiment.com/", "/").replace(
					"rtcmulticonnection.org/", "/") + "</a>"
};
var urlRegex = /\(?\b(?:(http|https|ftp):\/\/)?((?:www.)?[a-zA-Z0-9\-\.]+[\.][a-zA-Z]{2,4})(?::(\d*))?(?=[\s\/,\.\)])([\/]{1}[^\s\?]*[\/]{1})*(?:\/?([^\s\n\?\[\]\{\}\#]*(?:(?=\.)){1}|[^\s\n\?\[\]\{\}\.\#]*)?([\.]{1}[^\s\?\#]*)?)?(?:\?{1}([^\s\n\#\[\]\(\)]*))?([\#][^\s\n]*)?\)?/gi;
getCommonjs()