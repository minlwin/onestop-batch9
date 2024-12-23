import { Component, computed, signal } from '@angular/core';
import { WidgetsModule } from '../../widgets/widgets.module';
import { LoginUserState } from '../../services/security/login-user.state';
import { FormBuilder, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { PageInfo } from '../../services/commons';
import { AccountManagementService } from '../../services/api/account-management.service';
import { PagerComponent } from '../../widgets/pagination/pagination.component';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  standalone: true,
  imports: [WidgetsModule, ReactiveFormsModule],
  templateUrl: './admin.component.html',
  styles: ``
})
export class AdminComponent implements PagerComponent{

  form:FormGroup
  pageInfo = signal<PageInfo | undefined>(undefined)
  content = computed(() => this.pageInfo()?.contents || [])

  constructor(
    builder:FormBuilder,
    private service:AccountManagementService,
    private router:Router,
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

  onLinkChange(page: number): void {
    this.form.patchValue({page: page})
    this.search()
  }

  onSizeChange(size: number): void {
    this.form.patchValue({size: size, page: 0})
    this.search()
  }

  search() {
    this.service.search(this.form.value).subscribe(result => {
      this.pageInfo.set(result)
    })
  }

  signOut() {
    this.loginUser.signOut()
    this.router.navigate(['/anonymous'])
  }
}
