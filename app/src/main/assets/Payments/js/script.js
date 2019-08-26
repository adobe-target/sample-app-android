function toggleAccord(thisObj){
    if($(thisObj).closest('.payment-option-list-item').hasClass('active')){
        $(thisObj).closest('.payment-option-list-item').removeClass('active');
        $(thisObj).closest('.payment-option-list-item').find('.card-content').slideUp();
    }
    else{
        $('.payment-option-list-item').not($(thisObj).closest('.payment-option-list-item')).removeClass('active');
        $('.card-content').not($(thisObj).closest('.payment-option-list-item').find('.card-content')).slideUp();
        $(thisObj).closest('.payment-option-list-item').addClass('active');
        $(thisObj).closest('.payment-option-list-item').find('.card-content').slideDown(); 
    }
    
    if($('.payment-option-list-item.active').length){
        $('footer .button-container').removeClass('hidden');
    }
    else{
        $('footer .button-container').addClass('hidden');
    }
}

function androidBackPress(){
    if(typeof Android != "undefined"){
        Android.onBackPress();
    }
}

function androidProceed(){
    if(typeof Android != "undefined"){
        Android.onProceed();
    }
}