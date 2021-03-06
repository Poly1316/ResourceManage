function form2Json(formName){
    var form=document.getElementById(formName);
    var sb=new StringBuilder();var rcs=new Map();
    for ( var i = 0; i < form.elements.length; i++){
        var element = form.elements[i];    var name = element.name;
        if (typeof (name) === "undefined" || (name === null) || (name.length === 0)){continue;}
        var tagName = element.tagName;
        if(tagName ==='INPUT'||tagName === 'TEXTAREA'){var type = element.type;
            if ((type === 'text')||(type === 'password') || (type === 'datetime-local' ) || (type === 'hidden') || (tagName === 'TEXTAREA')){
                sb.append("\""+name+"\":\""+encodeURIComponent(element.value.replace(/\r\n/ig,""))+"\"");
            }else if((type === 'checkbox') || (type === 'radio')){
                rcs.putOverride(name,type);
            }else{continue;}
        }else if (tagName === 'SELECT'){var oc = element.options.length;
            for ( var j = 0; j <oc; j++){
                if (element.options[j].selected){sb.append("\""+name+"\":\""+(element.value)+"\"");}
            }
        }
    }
    if(rcs.size()>0){
        for(var i=0;i<rcs.size();i++){
            var r=rcs.element(i);var radio="";
            var d=document.getElementsByName(r.key);
            if(r.value==="radio"){
                for(j=0;j<d.length;j++){
                    if(d[j].checked){radio=d[j].value;}
                }
            }else{
                for(j=0;j<d.length;j++){
                    if(d[j].checked){radio+=","+d[j].value;}
                }
                radio.substr(1);
            }
            sb.append("\""+r.key+"\":\""+radio+"\"");
        }
    }
    return "{"+sb.toJsonString()+"}";
}


function StringBuilder(){
    this._element_ = new Array();
    this.append = function(item) {
        this._element_.push(item);
    }
    this.toString = function() {
        return this._element_.join("");
    }
    this.toJsonString = function() {
        return this._element_.join(",");
    }
    this.join = function(separator) {
        return this._element_.join(separator);
    }
    this.length = function() {
        return this._element_.length;
    }
}
function Map() {
    this.elements = new Array();
    // ??????MAP????????????
    this.size = function() {
        return this.elements.length;
    }
    // ??????MAP????????????
    this.isEmpty = function() {
        return (this.elements.length < 1);
    }
    // ??????MAP????????????
    this.clear = function() {
        this.elements = new Array();
    }
    // ???MAP??????????????????key, value)
    this.put = function(_key, _value) {
        this.elements.push({key : _key,value : _value});
    }
    //?????????????????????
    this.putOverride = function(_key,_value){
        this.remove(_key);
        this.put(_key,_value);
    }
    // ????????????KEY????????????????????????True???????????????False
    this.remove = function(_key) {
        var bln = false;
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    this.elements.splice(i, 1);
                    return true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    }

    // ????????????KEY????????????VALUE???????????????NULL
    this.get = function(_key) {
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    return this.elements[i].value;
                }
            }
        } catch (e) {
            return null;
        }
    }
    // ????????????????????????????????????element.key???element.value??????KEY???VALUE??????????????????NULL
    this.element = function(_index) {
        if (_index < 0 || _index >= this.elements.length) {
            return null;
        }
        return this.elements[_index];
    }
    // ??????MAP?????????????????????KEY?????????
    this.containsKey = function(_key) {
        var bln = false;
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].key == _key) {
                    bln = true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    }
    // ??????MAP?????????????????????VALUE?????????
    this.containsValue = function(_value) {
        var bln = false;
        try {
            for (i = 0; i < this.elements.length; i++) {
                if (this.elements[i].value == _value) {
                    bln = true;
                }
            }
        } catch (e) {
            bln = false;
        }
        return bln;
    }
    // ??????MAP?????????VALUE????????????ARRAY???
    this.values = function() {
        var arr = new Array();
        for (i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].value);
        }
        return arr;
    }
    // ??????MAP?????????KEY????????????ARRAY???
    this.keys = function() {
        var arr = new Array();
        for (i = 0; i < this.elements.length; i++) {
            arr.push(this.elements[i].key);
        }
        return arr;
    }
}