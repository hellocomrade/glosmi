var ogtddt=document.getElementById('orgglctagsdatedisplaytag');
if(ogtddt!=null)
{
    if(ddtag_dateStr!=null)
    {
        var value=ddtag_dateStr+' GMT';
        var result=Date.parse(value);
        if(result!=null)
            ogtddt.innerHTML=result.toString("yyyy-MM-dd h:mm:ss tt");

    }
    else
        ogtddt.innerHTML='Never';
}