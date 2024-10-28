import { Component, computed, signal } from '@angular/core';
import { WidgetsModule } from '../../widgets/widgets.module';
import { LoginUserState } from '../../services/security/login-user.state';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { PageInfo } from '../../services/api';
import { AccountManagementService } from '../../services/api/account-management.service';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [WidgetsModule, ReactiveFormsModule],
  templateUrl: './admin.component.html',
  styles: ``
})
export class AdminComponent {

  form:FormGroup
  pageInfo = signal<PageInfo | undefined>(undefined)
  content = computed(() => this.pageInfo()?.contents || [])

  constructor(
    builder:FormBuilder,
    private service:AccountManagementService,
    private loginUser:LoginUserState) {
      this.form = builder.group({
        entryFrom: '',
        entryTo: '',
        lastAccessFrom: '',
        lastAccessTo: '',
        keyword: '',
        page: 0,
        size: 10
      })

      this.search()
  }

  search() {
    this.service.search(this.form.value).subscribe(result => {
      this.pageInfo.set(result)
    })
  }

  signOut() {
    this.loginUser.signOut()
  }
}
