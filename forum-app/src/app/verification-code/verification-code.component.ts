import { Component, Output, EventEmitter } from '@angular/core';

@Component({
  selector: 'app-verification-code',
  templateUrl: './verification-code.component.html',
  styleUrls: ['./verification-code.component.css']
})
export class VerificationCodeComponent {
  code: string = '';
  @Output() verifyCode = new EventEmitter<string>();

  onSubmit() {
    this.verifyCode.emit(this.code);  // Emit the code for the parent component to handle
  }
}
