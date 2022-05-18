import { JwtInterceptor } from './interceptor/jwt.interceptor';
import { NgModule } from '@angular/core';
import { BrowserModule } from '@angular/platform-browser';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { LoginComponent } from './screens/login/login.component';
import { ClientComponent } from './screens/client/client.component';
import { AdminComponent } from './screens/admin/admin.component';
import { HomeComponent } from './screens/home/home.component';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { HttpClientModule, HTTP_INTERCEPTORS } from '@angular/common/http';
import { SsoComponent } from './screens/sso/sso.component';

@NgModule({
  declarations: [
    AppComponent,
    LoginComponent,
    ClientComponent,
    AdminComponent,
    HomeComponent,
    SsoComponent,

  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    FormsModule,
    ReactiveFormsModule,
    HttpClientModule
  ],
  providers: [
    {
      provide: HTTP_INTERCEPTORS, // special keyword
      useClass: JwtInterceptor,
      multi: true, // allows multiple interceptors
    }
  ],
  bootstrap: [AppComponent]
})
export class AppModule { }
