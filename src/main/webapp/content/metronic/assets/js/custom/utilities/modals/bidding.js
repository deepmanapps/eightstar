'use strict';
var KTModalBidding = (function () {
  var t, e, n;
  const o = () => {
    const t = e.querySelectorAll('[data-kt-modal-bidding-type] select');
    if (!t) return;
    t.forEach(t => {
      $(t).select2({
        minimumResultsForSearch: 1 / 0,
        templateResult: function (t) {
          return (t => {
            if (!t.id) return t.text;
            var e = 'assets/media/' + t.element.getAttribute('data-kt-bidding-modal-option-icon'),
              n = $('<img>', { class: 'rounded-circle me-2', width: 26, src: e }),
              o = $('<span>', { text: ' ' + t.text });
            return o.prepend(n), o;
          })(t);
        },
      });
    });
  };
  return {
    init: function () {
      (t = document.querySelector('#kt_modal_bidding')),
        (e = document.getElementById('kt_modal_bidding_form')),
        (n = new bootstrap.Modal(t)),
        e &&
          ((() => {
            const t = e.querySelectorAll('.required');
            var o,
              r = {
                fields: {},
                plugins: {
                  trigger: new FormValidation.plugins.Trigger(),
                  bootstrap: new FormValidation.plugins.Bootstrap5({ rowSelector: '.fv-row', eleInvalidClass: '', eleValidClass: '' }),
                },
              };
            t.forEach(t => {
              const e = t.closest('.fv-row').querySelector('input');
              e && (o = e);
              const n = t.closest('.fv-row').querySelector('textarea');
              n && (o = n);
              const i = t.closest('.fv-row').querySelector('select');
              i && (o = i);
              const a = o.getAttribute('name');
              r.fields[a] = { validators: { notEmpty: { message: t.innerText + ' is required' } } };
            });
            var i = FormValidation.formValidation(e, r);
            const a = e.querySelector('[data-kt-modal-action-type="submit"]');
            a.addEventListener('click', function (t) {
              t.preventDefault(),
                i &&
                  i.validate().then(function (t) {
                    console.log('validated!'),
                      'Valid' == t
                        ? (a.setAttribute('data-kt-indicator', 'on'),
                          (a.disabled = !0),
                          setTimeout(function () {
                            a.removeAttribute('data-kt-indicator'),
                              (a.disabled = !1),
                              Swal.fire({
                                text: 'Form has been successfully submitted!',
                                icon: 'success',
                                buttonsStyling: !1,
                                confirmButtonText: 'Ok, got it!',
                                customClass: { confirmButton: 'btn btn-primary' },
                              }).then(function () {
                                e.reset(), n.hide();
                              });
                          }, 2e3))
                        : Swal.fire({
                            text: 'Oops! There are some error(s) detected.',
                            icon: 'error',
                            buttonsStyling: !1,
                            confirmButtonText: 'Ok, got it!',
                            customClass: { confirmButton: 'btn btn-primary' },
                          });
                  });
            });
          })(),
          o(),
          (() => {
            const t = e.querySelectorAll('[data-kt-modal-bidding="option"]'),
              n = e.querySelector('[name="bid_amount"]');
            t.forEach(t => {
              t.addEventListener('click', t => {
                t.preventDefault(), (n.value = t.target.innerText);
              });
            });
          })(),
          (() => {
            const t = e.querySelector('.form-select[name="currency_type"]');
            $(t).on('select2:select', function (t) {
              const e = t.params.data;
              n(e);
            });
            const n = t => {
              console.log(t),
                e.querySelectorAll('[data-kt-modal-bidding-type]').forEach(e => {
                  e.classList.add('d-none'), e.getAttribute('data-kt-modal-bidding-type') === t.id && e.classList.remove('d-none');
                });
            };
          })(),
          (() => {
            const o = t.querySelector('[data-kt-modal-action-type="cancel"]'),
              r = t.querySelector('[data-kt-modal-action-type="close"]');
            o.addEventListener('click', t => {
              i(t);
            }),
              r.addEventListener('click', t => {
                i(t);
              });
            const i = t => {
              t.preventDefault(),
                Swal.fire({
                  text: 'Are you sure you would like to cancel?',
                  icon: 'warning',
                  showCancelButton: !0,
                  buttonsStyling: !1,
                  confirmButtonText: 'Yes, cancel it!',
                  cancelButtonText: 'No, return',
                  customClass: { confirmButton: 'btn btn-primary', cancelButton: 'btn btn-active-light' },
                }).then(function (t) {
                  t.value
                    ? (e.reset(), n.hide())
                    : 'cancel' === t.dismiss &&
                      Swal.fire({
                        text: 'Your form has not been cancelled!.',
                        icon: 'error',
                        buttonsStyling: !1,
                        confirmButtonText: 'Ok, got it!',
                        customClass: { confirmButton: 'btn btn-primary' },
                      });
                });
            };
          })());
    },
  };
})();
KTUtil.onDOMContentLoaded(function () {
  KTModalBidding.init();
});
