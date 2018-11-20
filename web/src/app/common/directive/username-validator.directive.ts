import { Directive, forwardRef, Input } from '@angular/core';
import { Validator, AbstractControl, NG_VALIDATORS, ValidatorFn } from '@angular/forms';

@Directive({
	selector: '[appForbiddenName]',
	providers: [
		{
			provide: NG_VALIDATORS,
			useExisting: UsernameValidatorDirective,
			multi: true
		}
	]
})
export class UsernameValidatorDirective implements Validator {

	@Input('appForbiddenName') forbiddenName: string;

	constructor() { }

	validate(control: AbstractControl): { [key: string]: any } {
		return this.forbiddenName ? forbiddenNameValidator(new RegExp(this.forbiddenName, 'i'))(control) : null;
	}
}

export function forbiddenNameValidator(nameRe: RegExp): ValidatorFn {
	return (control: AbstractControl): { [key: string]: any } => {
		const forbidden = nameRe.test(control.value);
		return forbidden ? { 'forbiddenName': { value: control.value } } : null;
	};
}
