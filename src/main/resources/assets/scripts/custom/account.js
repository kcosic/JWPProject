let allViews = ["details", "address", "history", "admin"];
let allAdminViews = ["admin-history", "admin-items", "admin-categories", "admin-customers", "admin-logs"];
let allDetailsField = ["firstName", "lastName","dateOfBirth"];
let currentView = passedView !== undefined && passedView !== "" ? passedView : allViews[0];
let currentAdminView = passedAdminView !== undefined && passedAdminView !== "" ? passedAdminView :allAdminViews[0];
let enableDetailsEdit;
function toggleViews() {
    if(enableDetailsEdit !== undefined){
        enableDetailsEdit = false;
    }
    allViews.forEach((type)=>{
        if(type === currentView){
            $("#" + type).show();
            $("#btn-" + type).addClass("btn-primary").removeClass("btn-info");
        }
        else{
            $("#" + type).hide();
            $("#btn-" + type).removeClass("btn-primary").addClass("btn-info");
        }
    })
}

function toggleAdminViews() {
    allAdminViews.forEach((type)=>{
        if(type === currentAdminView){
            $("#" + type).show();
            $("#btn-" + type).addClass("active");
        }
        else{
            $("#" + type).hide();
            $("#btn-" + type).removeClass("active");
        }
    })
}

function adminDisplay(newType){
    currentAdminView = newType;
    toggleAdminViews();
}

function display(newType){
    currentView = newType;
    toggleViews();
}

$(function(){
    toggleViews();
    toggleAdminViews();
    toggleDetailsFields();
})

function toggleDetailsFields(){
    if(enableDetailsEdit === undefined){
        enableDetailsEdit = false;
    } else {
        enableDetailsEdit = !enableDetailsEdit;
    }
    allDetailsField.forEach((field)=>{
        $("#"+field).prop("disabled", !enableDetailsEdit);
    })
    if(enableDetailsEdit){
        $("#btnSaveDetails").show();
        $("#emailWrapper").hide();
    }
    else {
        $("#btnSaveDetails").hide();
        $("#emailWrapper").show();
    }
}
