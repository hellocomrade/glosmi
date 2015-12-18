function jscss(a,o,c1,c2)
{
  		switch (a){
    		case 'swap':
      			o.className=!jscss('check',o,c1)?o.className.replace(c2,c1):o.className.replace(c1,c2);
    			break;
    		case 'add':
      			if(!jscss('check',o,c1)){o.className+=o.className?' '+c1:c1;}
    			break;
    		case 'remove':
      			var rep=o.className.match(' '+c1)?' '+c1:c1;
      			o.className=o.className.replace(rep,'');
    		break;
    		case 'check':
      			return new RegExp('\\b'+c1+'\\b').test(o.className)
    			break;
  		}
}
function textBoxError(jqid)
{
	if(jqid.hasClass('textbox-app'))
			jqid.removeClass('textbox-app');
	if(jqid.hasClass('textbox-error')==false)
		jqid.addClass('textbox-error');
}
function textBoxResume(jqid)
{
    if(jqid.hasClass('textbox-error'))
			jqid.removeClass('textbox-error');
	if(jqid.hasClass('textbox-app')==false)
		jqid.addClass('textbox-app');
}
function parseFieldError(data,errd)
{
	if(data==null||data==""||data=="undefined"||errd==null||errd==""||errd=="undefined")
		return;
	var len=data.length;
	var msg="";
	for(i=0;i<len;++i)
	{
		textBoxError($("#"+data[i].id));
		msg+=data[i].err+"<br/>";
	}
	errd.html(msg);
}
function validatePhone(phone)
{
	var regex=/^(?:\+?1[-. ]?)?\(?([0-9]{3})\)?[-. ]?([0-9]{3})[-. ]?([0-9]{4})$/i;
	return regex.test(phone);
}
function validateUSZipcode(zip)
{
	var regex=/^\d{5}(-\d{4})?$/i;
	return regex.test(zip);
}
function validateCAPostalcode(pcode)
{
	var regex=/^([A-Z]\d[A-Z]\s\d[A-Z]\d)$/i;
	return regex.test(pcode);
}
function validateEmail(email)
{
		var regex=/^((([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+(\.([a-z]|\d|[!#\$%&'\*\+\-\/=\?\^_`{\|}~]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])+)*)|((\x22)((((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(([\x01-\x08\x0b\x0c\x0e-\x1f\x7f]|\x21|[\x23-\x5b]|[\x5d-\x7e]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(\\([\x01-\x09\x0b\x0c\x0d-\x7f]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF]))))*(((\x20|\x09)*(\x0d\x0a))?(\x20|\x09)+)?(\x22)))@((([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|\d|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.)+(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])|(([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])([a-z]|\d|-|\.|_|~|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])*([a-z]|[\u00A0-\uD7FF\uF900-\uFDCF\uFDF0-\uFFEF])))\.?$/i;
		return regex.test(email);
}
function validateUrl(url)
{
		var regex=/^(http:\/\/|ftp:\/\/|https:\/\/)?[\w\-_]+(\.[\w\-_]+)+([\w\-\.,@?^=%&amp;:/~\+#]*[\w\-\@?^=%&amp;/~\+#])?/i;
		return regex.test(url);
}
function getURLParam(strParamName)
{
        var strReturn = "";
        var strHref = window.location.href;
        
        if ( strHref.indexOf("?") > -1 )
        {
            var strQueryString = strHref.substr(strHref.indexOf("?")).toLowerCase();
            var aQueryString = strQueryString.split("&");
            for ( var iParam = 0; iParam < aQueryString.length; iParam++ )
            {
                if (aQueryString[iParam].indexOf(strParamName.toLowerCase() + "=") > -1 )
                {
                    var aParam = aQueryString[iParam].split("=");
                    strReturn = aParam[1];
                    break;
                }
            }
       }
       return strReturn;
}

