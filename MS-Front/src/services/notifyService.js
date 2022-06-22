import Swal from 'sweetalert2/dist/sweetalert2.js'

export default {

    ErrorToast(title, msg) {
        Swal.fire({
            icon: 'error',
            title: title,
            text: msg,
          });
    },
    SuccessToast(title, msg) {
        Swal.fire({
            icon: 'success',
            title: title,
            text: msg,
          });   
    }

}