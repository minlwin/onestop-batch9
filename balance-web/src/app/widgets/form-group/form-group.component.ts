import { Component, input } from '@angular/core';

@Component({
  selector: 'app-form-group',
  templateUrl: './form-group.component.html',
  styles: ``
})
export class FormGroupComponent {

  label = input.required<string>()
  valid = input<boolean | undefined>()

}
