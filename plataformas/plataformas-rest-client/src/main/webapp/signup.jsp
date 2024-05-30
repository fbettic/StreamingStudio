<%@ page language="java" contentType="text/html; charset=UTF-8"
pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
  <head>
    <meta charset="UTF-8" />
    <title>Netflix | Signup</title>

    <link
      href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/css/bootstrap.min.css"
      rel="stylesheet"
    />
    <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0-beta2/dist/js/bootstrap.bundle.min.js"></script>

    <script
      type="text/javascript"
      src="https://code.jquery.com/jquery-3.6.0.min.js"
    ></script>
  </head>
  <body>
    <div class="container mt-5">
      <div class="row justify-content-center mb-5">
        <div class="d-flex justify-content-center">
          <img
            src="http://localhost:4210/images/logos/paramount-logo-title.svg"
            alt="logo"
            style="width: 200px"
          />
        </div>
      </div>
      <div class="row justify-content-center">
        <div class="col-md-6">
          <div class="card">
            <div class="card-header bg-primary text-white">
              <h3 class="text-center">Signup</h3>
            </div>
            <div class="card-body">
              <form action="signup" method="post">
                <div class="mb-3">
                  <label for="firstname" class="form-label">First Name:</label>
                  <input
                    type="text"
                    class="form-control"
                    id="firstname"
                    name="firstname"
                    required
                  />
                </div>
                <div class="mb-3">
                  <label for="lastname" class="form-label">Last Name:</label>
                  <input
                    type="text"
                    class="form-control"
                    id="lastname"
                    name="lastname"
                    required
                  />
                </div>
                <div class="mb-3">
                  <label for="email" class="form-label">Email:</label>
                  <input
                    type="email"
                    class="form-control"
                    id="email"
                    name="email"
                    required
                  />
                </div>
                <div class="mb-3">
                  <label for="password" class="form-label">Password:</label>
                  <input
                    type="password"
                    class="form-control"
                    id="password"
                    name="password"
                    required
                  />
                </div>
                <div class="mb-3">
                  <label for="phone" class="form-label">Phone:</label>
                  <input
                    type="text"
                    class="form-control"
                    id="phone"
                    name="phone"
                  />
                </div>
                <input type="hidden" name="uuid" value="${param.uuid}" />
                <div class="text-center">
                  <button type="submit" class="btn btn-primary">Submit</button>
                </div>
              </form>
            </div>
          </div>
        </div>
      </div>
    </div>
    <div
      class="modal fade"
      id="errorModal"
      tabindex="-1"
      aria-labelledby="errorModalLabel"
      aria-hidden="true"
    >
      <div class="modal-dialog">
        <div class="modal-content">
          <div class="modal-header">
            <h5 class="modal-title" id="errorModalLabel">Error</h5>
            <button
              type="button"
              class="btn-close"
              data-bs-dismiss="modal"
              aria-label="Close"
            ></button>
          </div>
          <div class="modal-body">${error}</div>
          <div class="modal-footer">
            <button
              type="button"
              class="btn btn-secondary"
              data-bs-dismiss="modal"
            >
              Close
            </button>
          </div>
        </div>
      </div>
    </div>

    <% if (request.getAttribute("error") != null) { %>
    <script type="text/javascript">
      $(document).ready(function () {
        var myModal = new bootstrap.Modal(
          document.getElementById("errorModal"),
          {
            keyboard: false,
          }
        );
        myModal.show();
      });
    </script>
    <% } %>
  </body>
</html>
