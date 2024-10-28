import { Injectable } from "@angular/core";
import { PageInfo } from "../commons";
import { HttpClient } from "@angular/common/http";
import { environment } from "../../../environments/environment";

const URL = `${environment.baseUrl}/account`

@Injectable({providedIn: 'root'})
export class AccountManagementService {

  constructor(private http:HttpClient) {}

  search(form:any) {
    return this.http.get<PageInfo>(URL, {params: form})
  }
}
