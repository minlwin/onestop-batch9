import { Routes } from "@angular/router";
import { HomeComponent } from "./home/home.component";
import { BalanceReportComponent } from "./balance-report/balance-report.component";
import { BalanceManagementComponent } from "./balance-management/balance-management.component";
import { LedgerManagementComponent } from "./ledger-management/ledger-management.component";
import { ChangePasswordComponent } from "./change-password/change-password.component";
import { BalanceEditComponent } from "./balance-management/balance-edit/balance-edit.component";
import { BalanceDetailsComponent } from "./balance-management/balance-details/balance-details.component";

export const routes:Routes = [
  {path: 'home', component: HomeComponent, title: "Home"},
  {path: 'report', component: BalanceReportComponent, title: "Report"},
  {path: 'management', title: "Management", children: [
    {path: 'details/:id', component: BalanceDetailsComponent},
    {path: 'list/:type', component: BalanceManagementComponent},
    {path: 'edit/:type', component: BalanceEditComponent},
    {path: '', redirectTo: '/members/management/list/Debit', pathMatch: 'full'}
  ]},
  {path: 'ledger', component: LedgerManagementComponent, title: "Ledgers"},
  {path: 'password', component: ChangePasswordComponent, title: "Password"},
  {path: '', redirectTo: '/members/home', pathMatch: 'full'}
]
